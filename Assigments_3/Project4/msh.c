#include <stdio.h>
#include <stdlib.h>     // exit
#include <signal.h>     // signals
#include <unistd.h>     // forking
#include <sys/types.h>  // pid_t

#include <dlfcn.h>
#include <sys/wait.h>   // wait
#include <ctype.h>
#include <string.h>
//gcc -c msh.c -o msh.o
//gcc msh.o -o msh

int main() {
    char *command = (char *)malloc(200 * sizeof(char)+1);
    char *commandSave = (char *)malloc(200 * sizeof(char)+1);
    char **arguments = (char **)malloc(20 * sizeof(char *)); // all args in an array
    char **pluginList =(char **)malloc(10 * sizeof(char *));//.so version of the plugin
    void **handleList = malloc(sizeof(void *) * 10);
    int pluginNum = 0; //0-9
    int handleNum=0;
     if (arguments == NULL||command==NULL ||commandSave==NULL) {
        perror("Failed to allocate memory");
        return 1;
    }
    while (1) { 
        printf(">");              
        fgets(command, 200, stdin);// get the command
        int len = strlen(command);
        if (len > 0 && command[len - 1] == '\n') {//strip the new line for aethetics         
            command[len - 1] = '\0';
        }
        //ignoreSpaces(command); ignore this issue can't be bothered
        strncpy(commandSave,command,200);
        char *token = strtok(command, " ");//split byspace
        if (token != NULL) {
            arguments[0] = token; //first token if it exists
            int i = 1;
        
            while ((token = strtok(NULL, " ")) != NULL) {
                arguments[i++] = token; // Store each subsequent token
            }
            arguments[i]=NULL;//end the result
        } else {
            printf("Enter some Command.\n");
        }

        //print check
        /*
        for (int j = 0; j < 10; j++) {
            if (arguments[j] != NULL) {
                printf("arguments[%d] = <%s>\n", j, arguments[j]);
            }
            else{
               
                break;
            }
        }
        */


        if (strncmp(arguments[0], "exit", 200) == 0) { // exit the shell
            printf("Exiting shell\n");
            break;                
        }

        else if (strncmp(arguments[0], "load", 4) == 0) { // load 
            //printf("loading some plugin\n");
            int noIssue = 1;//initiate at 0
            char plugin_path[500];//string path
            if (arguments[1] == NULL) {
                printf("Error: Plugin unknown initialization failed!\n");
            } 
            else//there is an arg
            {
                strcat(plugin_path,"./");
                strncat(plugin_path, (char*)arguments[1],20);
                strcat(plugin_path,".so");//pluginpath is now ./name.so
                int j=0;
                for(;j<10;j++)
                {
                    if(pluginList[j]==NULL)
                    {
                        break;
                    }
                    else{
                        if(strncmp(plugin_path,pluginList[j],20)==0)
                        {
                            printf("Error: Plugin %s initialization failed!\n", arguments[1]);
                            noIssue=0; 
                            break;
                        }
                    }
                } 
                if(noIssue==1)//if it did not fail
                    pluginList[j]=plugin_path;
                //printf("plugin path: %s\n",plugin_path);
                
                void *handle = dlopen(plugin_path, RTLD_LAZY);
                if (!handle) {
                    printf("Error: Plugin %s initialization failed!\n", arguments[1]);
                    noIssue=0;
                }
                handleList[handleNum]=handle;
                handleNum+=1;
                
                int (*initialize)() = dlsym(handle, "initialize");
                if ( noIssue==1 && dlerror() != NULL) {
                    printf("Error: Plugin %s initialization failed!\n", arguments[1]);
                    //dlclose(handle);
                    noIssue=0;
                }
        
                if(noIssue==1)
                {
                    int temp =initialize();
                    if (temp!=0)
                    {
                            printf("Error: Plugin %s initialization failed!\n", arguments[1]);
                    }
                    //dlclose(handle);
                }
           //printf("plugin name: %s\n",plugin_path);
            }
            memset(plugin_path, 0, sizeof(plugin_path));
           
            
        }
        else// not exit or load, execcommand
        {//limits
            char programName[20];
            //printf("Command: %s; ", arguments[0]);
            //printf("Arguments: %s\n",commandSave);
            pid_t pid = fork();

            if (pid == 0) {
                // child
                
                int realCommand =execvp(arguments[0], arguments);
                if(1||realCommand!=0)
                {// a plug in being run
                     //assume not initialized
                    int initialized = 0;
                    //printf("not a real command\n");
                    char plugin_path[500];//string path
                    strcat(plugin_path,"./");
                    strcat(plugin_path, (char*)arguments[0]);
                    strcat(plugin_path,".so");
                    for(int j = 0;j<10;j++)
                    {
                         if(strncmp(plugin_path,pluginList[j],20)==0)
                        {
                            initialized=1;
                            break;
                        }
                    }
                    if(initialized==1)
                    {
                        void *handle = dlopen(plugin_path, RTLD_LAZY);
                        memset(plugin_path, 0, sizeof(plugin_path));
                        int (*run)() = dlsym(handle, "run");
                        if ( dlerror() != NULL) {
                            fprintf(stderr, "Run Failed %s\n",dlerror());
                            //dlclose(handle);
                            exit(1);
                        }
                        run(arguments);
                        //dlclose(handle);
                    }
                    //printf("%s\n",plugin_path);
                  
                }
                exit(0);
            }
            else {
                // parent
                //printf("parent\n");
                int status;
                waitpid(pid, &status, 0);
                //printf("Done!\n")           
            }        
        }
    }    
    for(int i =0;i<10;i++)
    {
        if(handleList[i]==NULL)
        {
            break;
        }
        else
        {
            //printf("Handle removed");
            dlclose(handleList[i]);
        }
    }
    free(handleList);
    free(pluginList);
    free(arguments);
    free(command);
    free(commandSave);
    return 0;
}