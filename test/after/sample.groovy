import groovy.json.*
import java.security.SecureRandom

def returnCode = 0
Exception eThrow = null
try {
  initialize(args)
} catch (Exception e) {
  eThrow = e
  returnCode = 1
}
if (eThrow == null) {
  return 0
}
else {
  throw eThrow
  return 1
}

// Check if it cares about Jenkins-specific control blocks
pipeline {
  agent {
    label 'foo'
  }
  options {
    timeout(time: 60, unit: 'MINUTES')
    timestamps()
  }

  stages {
    stage('bar') {
      steps {
        script {
          buildUtils = load('baz')
        }
      }
    }
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////// SCRIPT /////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
def initialize(args3) {   //
  def executor = new TestExecutor(args3)
  return executor
}

class TestExecutor {

  public TestExecutor(args2) {
    this.testExternalGlobalProps()
  }

  public testExternalGlobalProps() {
    Utils.printlnLog('########## testExternalGlobalProps')
    def globalKeyName = new SecureRandom().with { (1..9).collect { (('a'..'z')).join()[ nextInt((('a'..'z')).join().length())] }.join() }
    Utils.printlnLog("Generated random key: ${globalKeyName}")
    Utils.setExternalValue(globalKeyName , 'lelama' , 'nul')
    def storedValue = Utils.getExternalValue(globalKeyName , 'lelama')
    assert storedValue == 'nul' , 'Error in global prop key storage/ retrieval (1)'
    Utils.setExternalValue(globalKeyName , 'lelama2' , 'nul2')
    def storedValue2 = Utils.getExternalValue(globalKeyName , 'lelama2')
    assert storedValue2 == 'nul2' , 'Error in global prop key storage/ retrieval (2)'
    def storedValueBack = Utils.getExternalValue(globalKeyName , 'lelama')
    assert storedValueBack == 'nul' , 'Error in global prop key storage/ retrieval (3)'
    Utils.printlnLog(Utils.getExternalValue(globalKeyName))
  }

}
