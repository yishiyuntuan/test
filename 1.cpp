// #include "clang/AST/ASTConsumer.h"
// #include "clang/AST/Decl.h"
// #include "clang/AST/DeclBase.h"
// #include "clang/AST/Expr.h"
// #include "clang/AST/RecursiveASTVisitor.h"
// #include "clang/Frontend/CompilerInstance.h"
// #include "clang/Frontend/FrontendAction.h"
// #include "clang/StaticAnalyzer/Core/Checker.h"
// #include "clang/StaticAnalyzer/Core/PathSensitive/CheckerContext.h"
// #include "clang/Tooling/Tooling.h"
// #include <iostream>

// #include "clang/AST/ParentMap.h"
// #include "clang/AST/Stmt.h"
// #include "clang/StaticAnalyzer/Core/BugReporter/BugType.h"
// #include "clang/StaticAnalyzer/Frontend/CheckerRegistry.h"
// #include "clang/StaticAnalyzer/Core/Checker.h"
// #include "clang/StaticAnalyzer/Core/BugReporter/BugReporter.h"
// #include "clang/AST/AST.h"
// #include "clang/AST/ASTConsumer.h"
// #include "clang/AST/StmtVisitor.h"
// #include "clang/Frontend/CompilerInstance.h"

// using namespace std;
// using namespace clang;
// using namespace ento;
// using namespace llvm;

// bool isSaveSensitiveInfomationStruct(const RecordDecl *RD) {
//   if (!RD->isStruct())
//     return false;
//   string Name = RD->getNameAsString();
//   if (Name == "passwd" || Name == "password") {
//     // 发现敏感信息结构体，进行告警
//     return true;
//   }
//   return false;
// }

// bool findClearCall(const FunctionDecl *FD, BugReporter &BR) {
//   // 判断结构体所在函数在返回前是否有对该结构体进行内存清零
//   return false;
// }

// namespace {
// class SensitiveDataChecker : public Checker<check::ASTDecl<RecordDecl>> {

// public:
//   SensitiveDataChecker() {}
//   // void checkRecordDecl(const RecordDecl *RD, CheckerContext &C);
//   void checkASTDecl(const RecordDecl *RD, AnalysisManager &Mgr,
//                     BugReporter &BR) const {
//     checkRecordDecl(RD, Mgr, BR);
//   }
//   void checkRecordDecl(const RecordDecl *RD, AnalysisManager &Mgr,
//                        BugReporter &BR) const;
//   mutable std::unique_ptr<BugType> BT;
//   private:
//   // 检查结构体是否为敏感数据类型
//   bool isSensitiveData(const RecordDecl *RD) const {
//     if (!RD->hasNameForLinkage()) {
//       return false;
//     }
//     return RD->getNameAsString() == "SensitiveData";
//   }
//   // 检查结构体是否被清零
//   bool isZeroed(const Expr *E, CheckerContext &C) const {
//     if (!E) {
//       return false;
//     }
//     SVal Val = C.getState()->getSVal(E, C.getLocationContext());
//     if (Val.isUnknownOrUndef()) {
//       return false;
//     }
//     // 检查是否为零值
//     return Val.isZeroConstant();
//   }
// };
// } // anonymous namespace

// void SensitiveDataChecker::checkRecordDecl(const RecordDecl *RD,
//                                            AnalysisManager &Mgr,
//                                            BugReporter &BR) const {

//   if (!isSaveSensitiveInfomationStruct(RD)) {
//     return;
//   }
//   // // 判断函数返回前是否进行了清零操作
//   // if (!findClearCall()) {
//   //   return;
//   // }
//   ParentMap PM(RD->getBody());
//   // 获取RecordDecl节点所在的函数
//   // const Decl *D = PM.getParent(RD);
//   // D->dump();

//   // 获取当前结构体所在的函数
//   // 获取RecordDecl节点所在函数
//   const DeclContext *DC = RD->getDeclContext();
//   while (DC && !isa<FunctionDecl>(DC)) {
//     DC = DC->getParent();
//   }
//   if (DC) {
//     const FunctionDecl *FD = dyn_cast<FunctionDecl>(DC);
//     // 对FunctionDecl进行处理
//     // ...
//     cout << "=============================" << endl;
//     FD->dump();
//     // FD->dumpCFG();
//     cout << "=============================" << endl;
//   }
// }

// // Register plugin
// extern "C" void clang_registerCheckers(CheckerRegistry &registry) {
//   registry.addChecker<SensitiveDataChecker>("test.StructSaveInfo","Checks for calls to main" ,"");
// }
// extern "C" const char clang_analyzerAPIVersionString[] =
//     CLANG_ANALYZER_API_VERSION_STRING;



// #include "clang/StaticAnalyzer/Core/Checker.h"
// #include "clang/StaticAnalyzer/Core/BugReporter/BugReporter.h"
// #include "clang/AST/AST.h"
// // #include "clang/AST/StmtCXX.h"
// #include "clang/AST/ASTConsumer.h"
// #include "clang/AST/StmtVisitor.h"
// #include "clang/Frontend/CompilerInstance.h"
// #include "clang/StaticAnalyzer/Core/PathSensitive/AnalysisManager.h"
// #include "clang/AST/Decl.h"
// #include "clang/AST/DeclBase.h"

// #include "clang/StaticAnalyzer/Frontend/CheckerRegistry.h"

// using namespace clang;
// using namespace ento;

// class SensitiveDataChecker : public Checker<check::ASTDecl<RecordDecl>> {
// public:
//   void checkASTDecl(const RecordDecl *RD, AnalysisManager &Mgr,
//                     BugReporter &BR) const {
//     if (!isSensitiveData(RD)) {
//       return;
//     }
//     // 检查结构体定义中是否有成员变量
//     if (RD->field_empty()) {
//       return;
//     }
//     // 检查结构体是否在退出函数之前被清零
//     AnalysisDeclContext *AC = Mgr.getAnalysisDeclContext(RD); 
//     const DeclContext *DC = RD->getDeclContext();
//     //const DeclContext *DC = AC->getDecl();
//     //const DeclContext *DC = Mgr.getAnalysisDeclContext(RD);
//     const FunctionDecl *FD = dyn_cast<FunctionDecl>(DC);
//     if (!FD) {
//       return;
//     }
//     if (!FD->hasBody()) {
//       return;
//     }
//     Stmt *Body = FD->getBody();
//     if (!Body) {
//       return;
//     }
//     Stmt *LastStmt = Body->getStmts().back();
//     if (!LastStmt) {
//       return;
//     }
//     if (!isa<ReturnStmt>(LastStmt)) {
//       return;
//     }
//     const ReturnStmt *RS = cast<ReturnStmt>(LastStmt);
//     if (!isZeroed(RS->getRetValue(), Mgr)) {
//       // 生成告警
//       PathDiagnosticLocation L =
//           PathDiagnosticLocation::createBegin(RD, BR.getSourceManager(),
//                                               Mgr.getAnalysisDeclContext(RD));
//       BR.EmitBasicReport(RD, this, "Sensitive data not cleared before return",
//                          "Security", "Sensitive data not cleared before return",
//                          L);
//     }
//   }

// private:
//   // 检查结构体是否为敏感数据类型
//   bool isSensitiveData(const RecordDecl *RD) const {
//     if (!RD->hasNameForLinkage()) {
//       return false;
//     }
//     return RD->getNameAsString() == "SensitiveData";
//   }
//   // 检查结构体是否被清零
//   bool isZeroed(const Expr *E, AnalysisManager &Mgr) const {
//     if (!E) {
//       return false;
//     }
//     //ProgramStateManager &StateMgr = Mgr.getStateManager();
//     //SVal Val = StateMgr.getValuation().getSVal(Reg, DC);


//     SVal Val = Mgr.getValuation().getValue(E);
//     if (Val.isUnknownOrUndef()) {
//       return false;
//     }
//     // 检查是否为零值
//     return Val.isZeroConstant();
//   }
// };




#include "clang/StaticAnalyzer/Frontend/CheckerRegistry.h"
#include "clang/StaticAnalyzer/Core/Checker.h"
#include "clang/StaticAnalyzer/Core/Checker.h"
#include "clang/StaticAnalyzer/Core/BugReporter/BugType.h"
#include "clang/StaticAnalyzer/Core/BugReporter/BugReporter.h"
#include "clang/AST/Decl.h"
#include "clang/AST/Stmt.h"
#include "llvm/Support/raw_ostream.h"

using namespace clang;
using namespace ento;

namespace {

class SensitiveDataChecker : public Checker<check::ASTCodeBody> {
public:
  void checkASTCodeBody(const Decl *D, AnalysisManager &Mgr, BugReporter &BR) const {
    // 查找所有的结构体定义
    for (const auto &DC : D->decls()) {
      const auto *TD = dyn_cast<TypedefNameDecl>(DC);
      if (!TD)
        continue;
      const auto *TT = TD->getUnderlyingType()->getAs<RecordType>();
      if (!TT)
        continue;
      const auto *RD = TT->getDecl()->getDefinition();
      if (!RD)
        continue;
      // 判断结构体是否存储敏感信息
      if (RD->getNameAsString() == "passwd") {
        bool HasClear = false;
        // 检查是否在函数返回之前对结构体进行了内存清零操作
        for (const auto *S : D->getBody()->children()) {
          if (isa<ReturnStmt>(S) || isa<CompoundStmt>(S))
            break;
          if (const auto *B = dyn_cast<BinaryOperator>(S)) {
            if (B->isAssignmentOp() && B->getLHS()->getType()->isPointerType() &&
                B->getLHS()->getType()->getPointeeType()->isStructureType() &&
                B->getLHS()->getType()->getPointeeType()->getAs<RecordType>()->getDecl() == RD &&
                B->getRHS()->isNullPointerConstant(Mgr.getASTContext(),
                                                   Expr::NPC_ValueDependentIsNull)) {
              HasClear = true;
              break;
            }
          }
        }
        if (!HasClear) {
          PathDiagnosticLocation PDL = PathDiagnosticLocation::createBegin(D, BR.getSourceManager());
          BR.EmitBasicReport(D, this, "Sensitive information not cleared",
                             "Security", "Sensitive information not cleared",
                             PDL);
        }
      }
    }
  }
};

} // namespace



// Register plugin
extern "C" void clang_registerCheckers(CheckerRegistry &registry) {
  registry.addChecker<SensitiveDataChecker>("test.StructSaveInfo","Checks for calls to main" ,"");
}
extern "C" const char clang_analyzerAPIVersionString[] =
    CLANG_ANALYZER_API_VERSION_STRING;

