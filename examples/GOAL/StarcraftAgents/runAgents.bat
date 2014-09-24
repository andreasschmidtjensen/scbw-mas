@echo off

IF "%GOALHOME%" == "" ( 
	echo %%GOALHOME%% was not defined, setting to C:\bin\eclipse\plugins\org.eclipse.gdt_1.1.0.201408181400\lib
	set GOALHOME=C:\bin\eclipse\plugins\org.eclipse.gdt_1.1.0.201408181400\lib
)

set LIBS=../../../libs

set PATH=.;%GOALHOME%/win32;%PATH%
set SWI_HOME_DIR=%GOALHOME%/win32

echo Running java -cp .;%GOALHOME%/goal.jar;%LIBS%/jnibwapi.jar -Djava.library.path=.;%GOALHOME%/win32 goal.tools.Run -p . 
java -cp .;%GOALHOME%/goal.jar;%LIBS%/jnibwapi.jar -Djava.library.path=.;%GOALHOME%/win32 goal.tools.eclipse.RunTool -p .

