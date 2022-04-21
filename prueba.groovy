job('Java Prueba App DSL 3') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/juanmazonflo/PruebaDSL3.git', 'main') { node ->
            node / gitConfigName('juanmazonflo')
            node / gitConfigEmail('juanitocapotillo@gmail.com')
        }
    }
    triggers {
	cron('H/6 * * * *')
    	githubPush()
    }    
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Entregaaa: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Prueba App DSL 3/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	      slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}

job('Job test Hola Mundo') {
	description('Aplicacion Hola Mundo de Prueba')
	triggers {
		cron('H/6 * * * *')
    		githubPush()
    	}
	steps {
		shell('''
			echo "HOLA Mundo!!!" 
		''')
	}
}
