project 'TestProjectLab', {
  tracked = '1'
  credential 'testprojectcred', userName: 'mk', {
    credentialType = 'LOCAL'
    acl {
      inheriting = '0'
      aclEntry 'user', principalName: 'admin', {
        changePermissionsPrivilege = 'allow'
        executePrivilege = 'allow'
        modifyPrivilege = 'allow'
        readPrivilege = 'allow'
      }
    }
  }
  procedure 'proc1_testprojectlab', {
    resourceName = 'local'
    timeLimit = '0'
    step 'step1', {
      command = 'echo "Hello World. This is the first procedure for this project"'
      resourceName = 'local'
      shell = 'bash'
      timeLimit = '0'
      timeLimitUnits = 'seconds'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  procedure 'proc1_testprojectlab Copy1', {
    resourceName = 'local'
    timeLimit = '0'
    step 'step1', {
      command = 'echo "Hello World"'
      resourceName = 'local'
      shell = 'bash'
      timeLimit = '0'
      timeLimitUnits = 'seconds'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  procedure 'proc2_testprojectlab Copy', {
    resourceName = 'local'
    timeLimit = '0'
    step 'step1', {
      command = 'echo "Hello World"'
      resourceName = 'local'
      shell = 'bash'
      timeLimit = '0'
      timeLimitUnits = 'seconds'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  procedure 'AAP_Workflow_Job_Wait', {
    resourceName = 'local'
    timeLimit = '0'
    step 'step1', {
      command = 'echo "Hello World"'
      resourceName = 'local'
      shell = 'bash'
      timeLimit = '0'
      timeLimitUnits = 'seconds'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  procedure 'AAP_Workflow_Job_Monitor', {
    resourceName = 'local'
    timeLimit = '0'
    step 'step1', {
      command = 'echo "Hello World"'
      resourceName = 'local'
      shell = 'bash'
      timeLimit = '0'
      timeLimitUnits = 'seconds'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  tag 'testprojectlab', {
    acl {
      inheriting = '1'
    }
  }
  workflowDefinition 'testworkflowsync', {
    stateDefinition 'New State', {
      subprocedure = 'proc1_testprojectlab'
      subproject = 'TestProjectLab'
      acl {
        inheriting = '1'
      }
    }
    stateDefinition 'New State 2', {
      startable = '0'
      subprocedure = 'proc2_testprojectlab Copy2'
      subproject = 'TestProjectLab'
      acl {
        inheriting = '1'
      }
    }
    stateDefinition 'New State 3', {
      startable = '0'
      subprocedure = 'proc3_testprojectlab Copy3'
      subproject = 'TestProjectLab'
      acl {
        inheriting = '1'
      }
    }
    tag 'sometag', {
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
   workflowDefinition 'Monitor_AAP_Workflow_Job', {
    stateDefinition 'Monitor_AAP_Workflow_Job', {
      subprocedure = 'AAP_Workflow_Job_Monitor'
      subproject = 'TestProjectLab'
      actualParameter 'id', '$[id]'
      actualParameter 'input_change_number', '$[inputChangeNumber]'
      actualParameter 'pipelineRunTimeId', '$[pipelineRunTimeId]'
      formalParameter 'id', {
        type = 'entry'
      }
      formalParameter 'inputChangeNumber', {
        required = '1'
        type = 'entry'
      }
      formalParameter 'pipelineRunTimeId', {
        type = 'entry'
      }
      // Custom properties
    }
    stateDefinition 'Wait', {
      startable = '0'
      subprocedure = 'AAP_Workflow_Job_Wait'
      subproject = 'TestProjectLab'
    }
    stateDefinition 'Finish', {
      startable = '0'
    }
    stateDefinition 'Fail', {
      startable = '0'
    }
    transitionDefinition 'In Progress', {
      condition = '$[/javascript mySubjob.outputParameters.status_for_workflow == \"in progress\";]'
      stateDefinitionName = 'Monitor_AAP_Workflow_Job'
      targetState = 'Wait'
      trigger = 'onCompletion'
    }
    transitionDefinition 'Complete', {
      condition = '$[/javascript mySubjob.outputParameters.status_for_workflow == \"complete\";]'
      stateDefinitionName = 'Monitor_AAP_Workflow_Job'
      targetState = 'Finish'
      trigger = 'onCompletion'
    }
    transitionDefinition 'Unexpected Failure', {
      condition = '$[/javascript mySubjob.outputParameters.status_for_workflow == \"unset\" || $[/javascript mySubjob.outputParameters.status_for_workflow == \"unexpected failure\";]'
      stateDefinitionName = 'Monitor_AAP_Workflow_Job'
      targetState = 'Fail'
      trigger = 'onCompletion'
    }
    transitionDefinition 'Retry', {
      stateDefinitionName = 'Wait'
      targetState = 'Monitor_AAP_Workflow_Job'
      trigger = 'onCompletion'
      actualParameter 'id', '$[id]'
      actualParameter 'inputChangeNumber', '$[inputChangeNumber]'
      actualParameter 'pipelineRunTimeId', '$[pipelineRunTimeId]'
    }
  }
  pipeline 'Check pipeline and flowruntime Copy', {
    formalParameter 'ec_stagesToRun', {
      expansionDeferred = '1'
    }
    stage 'Stage 1', {
      colorCode = '#289ce1'
      pipelineName = 'Check pipeline and flowruntime Copy'
      task 'check runtime ids', {
        command = '''ectool getProperty /myPipelineRuntime/stages/<stageName>/tasks/<taskName>/outcome
echo ${/myFlowRuntime/flowRuntimeId}
ectool getProperty --path /myPipelineRuntime'''
        resourceName = 'local'
        shell = 'bash'
        taskType = 'COMMAND'
        acl {
          inheriting = '1'
        }
      }
      acl {
        inheriting = '1'
      }
    }
    // Custom properties
    property 'ec_counters', {
      // Custom properties
      pipelineCounter = '1'
      acl {
        inheriting = '1'
      }
    }
    acl {
      inheriting = '1'
    }
  }
  acl {
    inheriting = '1'
    aclEntry 'user', principalName: 'project: TestProjectLab', {
      changePermissionsPrivilege = 'allow'
      executePrivilege = 'allow'
      modifyPrivilege = 'allow'
      readPrivilege = 'allow'
    }
  }
}
