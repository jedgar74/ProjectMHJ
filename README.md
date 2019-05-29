# ProjectMHJ
Para compilar el programa se ejecutan las siguientes instrucciones: 
``` [java]
javac -d bin/ -cp src src/*.java 
``` 
Debe tomar en cuenta que el código fuente se encuentra en el directorio *src*

Para realizar el proceso de ejecución se procede con el siguiente comando. 
``` [java]
java -cp bin ExecuteAll
```

Observe que el archivo de ejecución se denomina *ExecuteAll* para ejecutar varias metaheurísticas sobre mismo problema. Si desea ejecutar un solo método en un problema específico simplemente ejecuta el siguiente comando. 
``` [java]
java -cp bin ExecuteOne
```

Observe además qué debe editar y compilar inicialmente los archivos que usted desea ejecutar.
