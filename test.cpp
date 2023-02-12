#include "clang/AST/ASTConsumer.h"
#include "clang/AST/Decl.h"
#include "clang/AST/DeclBase.h"
#include "clang/AST/Expr.h"
#include "clang/AST/RecursiveASTVisitor.h"
#include "clang/Frontend/CompilerInstance.h"
#include "clang/Frontend/FrontendAction.h"
#include "clang/StaticAnalyzer/Core/Checker.h"
#include "clang/StaticAnalyzer/Core/PathSensitive/CheckerContext.h"
#include "clang/Tooling/Tooling.h"
#include <iostream>

#include "clang/AST/ParentMap.h"
#include "clang/AST/Stmt.h"
#include "clang/StaticAnalyzer/Core/BugReporter/BugType.h"
#include "clang/StaticAnalyzer/Frontend/CheckerRegistry.h"
using namespace std;
using namespace clang;
using namespace ento;
using namespace llvm;

bool isSaveSensitiveInfomationStruct(const RecordDecl *RD) {
  if (!RD->isStruct())
    return false;
  string Name = RD->getNameAsString();
  if (Name == "passwd" || Name == "password") {
    // 发现敏感信息结构体，进行告警
    return true;
  }
  return false;
}

bool findClearCall(const FunctionDecl *FD, BugReporter &BR) {
  // 判断结构体所在函数在返回前是否有对该结构体进行内存清零
  return false;
}

namespace {
class SensitiveDataChecker : public Checker<check::ASTDecl<RecordDecl>> {

public:
  SensitiveDataChecker() {}
  // void checkRecordDecl(const RecordDecl *RD, CheckerContext &C);
  void checkASTDecl(const RecordDecl *RD, AnalysisManager &Mgr,
                    BugReporter &BR) const {
    checkRecordDecl(RD, Mgr, BR);
  }
  void checkRecordDecl(const RecordDecl *RD, AnalysisManager &Mgr,
                       BugReporter &BR) const;
  mutable std::unique_ptr<BugType> BT;
  // private:
  //  判断是否是保持敏感信息的结构体
  // bool isSaveSensitiveInfomationStruct(const RecordDecl *RD);
  //  判断是否进行了清零操作
  // bool findClearCall();
};
} // anonymous namespace

void SensitiveDataChecker::checkRecordDecl(const RecordDecl *RD,
                                           AnalysisManager &Mgr,
                                           BugReporter &BR) const {

  if (!isSaveSensitiveInfomationStruct(RD)) {
    return;
  }
  // // 判断函数返回前是否进行了清零操作
  // if (!findClearCall()) {
  //   return;
  // }
  ParentMap PM(RD->getBody());
  // 获取RecordDecl节点所在的函数
  // const Decl *D = PM.getParent(RD);
  // D->dump();

  // 获取当前结构体所在的函数
  // 获取RecordDecl节点所在函数
  const DeclContext *DC = RD->getDeclContext();
  while (DC && !isa<FunctionDecl>(DC)) {
    DC = DC->getParent();
  }
  if (DC) {
    const FunctionDecl *FD = dyn_cast<FunctionDecl>(DC);
    // 对FunctionDecl进行处理
    // ...
    cout << "=============================" << endl;
    FD->dump();
    // FD->dumpCFG();
    cout << "=============================" << endl;
  }
}

// Register plugin
extern "C" void clang_registerCheckers(CheckerRegistry &registry) {
  registry.addChecker<SensitiveDataChecker>("test.StructSaveInfo","Checks for calls to main" ,"");
}
extern "C" const char clang_analyzerAPIVersionString[] =
    CLANG_ANALYZER_API_VERSION_STRING;
