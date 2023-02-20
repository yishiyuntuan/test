#include "clang/StaticAnalyzer/Core/BugReporter/BugType.h"
#include "clang/StaticAnalyzer/Core/Checker.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/CallEvent.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/CheckerContext.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ExprEngine.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ExprEngineBuilders.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ExprEngineCallAndReturn.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ExprEngineCXX.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ExprEngineObjC.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/ProgramState.h"
#include "llvm/ADT/Optional.h"
#include "llvm/Support/raw_ostream.h"

using namespace clang;
using namespace ento;

namespace {
    class MyChecker : public Checker<check::ASTCodeBody> {
        mutable std::unique_ptr<BuiltinBug> BT;
    public:
        void checkASTCodeBody(const Decl *D, AnalysisManager &AM, BugReporter &BR) const {
            if (!D->hasBody())
                return;
            const auto &C = D->getASTContext();
            const auto *TU = C.getTranslationUnitDecl();
            for (const auto *D : TU->decls()) {
                if (const auto *R = dyn_cast<RecordDecl>(D)) {
                    if (R->hasAttr<AnnotateAttr>() && R->hasDefinition()) {
                        const auto &VD = *R->field_begin();
                        const auto *FD = VD->getCanonicalDecl();
                        if (FD && FD->getType()->isPointerType()) {
                            const auto *FVD = dyn_cast_or_null<VarDecl>(VD);
                            if (FVD && FVD->hasInit()) {
                                const auto *Init = FVD->getInit();
                                const auto *Clear = dyn_cast_or_null<CallExpr>(Init);
                                if (Clear && Clear->getDirectCallee() && Clear->getDirectCallee()->getName() == "memset") {
                                    return;
                                }
                            }
                            if (!BT)
                                BT.reset(new BuiltinBug(this, "Sensitive information not cleared", "Memory leak of sensitive information"));
                            ExplodedNode *N = AM.getPathDiagnosticConsumer().getCheckerManager().generateErrorNode();
                            auto R = std::make_unique<PathSensitiveBugReport>(*BT, "Sensitive information not cleared", N);
                            BR.emitReport(std::move(R));
                        }
                    }
                }
            }
        }
    };
}

void ento::registerMyChecker(CheckerManager &Mgr) {
    Mgr.registerChecker<MyChecker>();
}



//最接近
#include "clang/StaticAnalyzer/Core/Checker.h"
#include "clang/StaticAnalyzer/Core/BugReporter/BugReporter.h"
#include "clang/AST/AST.h"
#include "clang/AST/ASTConsumer.h"
#include "clang/AST/StmtVisitor.h"
#include "clang/Frontend/CompilerInstance.h"

using namespace clang;
using namespace ento;

class SensitiveDataChecker : public Checker<check::PreStmt<ReturnStmt>> {
public:
  void checkPreStmt(const ReturnStmt *RS, CheckerContext &C) const {
    const FunctionDecl *FD = C.getCurrentAnalysisDeclContext()->getDecl();
    // 检查函数返回类型是否是结构体
    if (!FD->getReturnType()->isStructureOrClassType()) {
      return;
    }
    const RecordType *RT = FD->getReturnType()->getAs<RecordType>();
    RecordDecl *RD = RT->getDecl();
    // 检查结构体是否为敏感数据类型
    if (!isSensitiveData(RD)) {
      return;
    }
    // 检查结构体是否在返回前被清零
    if (!isZeroed(RS->getRetValue(), C)) {
      // 生成告警
      ExplodedNode *N = C.generateErrorNode();
      if (N) {
        std::string Msg = "Sensitive data not cleared before returning";
        BugReport *R = new BugReport(*this, Msg, N);
        C.emitReport(R);
      }
    }
  }
private:
  // 检查结构体是否为敏感数据类型
  bool isSensitiveData(const RecordDecl *RD) const {
    if (!RD->hasNameForLinkage()) {
      return false;
    }
    return RD->getNameForLinkage() == "SensitiveData";
  }
  // 检查结构体是否被清零
  bool isZeroed(const Expr *E, CheckerContext &C) const {
    if (!E) {
      return false;
    }
    SVal Val = C.getState()->getSVal(E, C.getLocationContext());
    if (Val.isUnknownOrUndef()) {
      return false;
    }
    // 检查是否为零值
    return Val.isZeroConstant();
  }
};

void registerSensitiveDataChecker(CheckerManager &Mgr) {
  Mgr.registerChecker<SensitiveDataChecker>();
}

extern "C"
void clang_registerCheckers(CheckerRegistry &Registry) {
  registerSensitiveDataChecker(Registry);
}
