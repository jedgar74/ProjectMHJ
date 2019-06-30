# ProjectMHJ
El proyecto consiste en desarrollar una plataforma para ejecutar y desarrollar diferentes  metaheurísticas para solucionar diferentes problemas monoobjetivo. Se desarrolló el software en Java 1.8 y en Eclipse Oxigen, se denominó ProjectMHJ. 

Para proceder a compilar el programa debe ubicarse en el directorio del proyecto, en nuestro caso ProjectMHJ y ejecutar la siguiente instrucción: 
``` [java]
javac -d bin/ -cp src src/*.java 
``` 
Debe tomar en cuenta que el código fuente se encuentra en el directorio *src*.

Se definen 2 formas para la ejecución del código. La primera utilizando una sola metaheuristica para un problema y la segunda definiendo la posibilidad detener varias metaheuristicas para la resolución de un problema. Para realizar el proceso de ejecución se procede con el siguiente comando. 
``` [java]
java -cp bin ExecuteAll
```

Observe que el archivo de ejecución se denomina *ExecuteAll* para ejecutar varias metaheurísticas sobre mismo problema. Si desea ejecutar un solo método en un problema específico simplemente ejecuta el siguiente comando. 
``` [java]
java -cp bin ExecuteOne
```

Observe además qué debe editar y compilar inicialmente los archivos que usted desea ejecutar.

## Ejemplos
``` [java]
 1. Agent agent = new Agent() 
 2.   .metaheuristic("SA")
 3.   .paraMetaheuristic("SAG")
 4.   .nEvals(125000)
 5.   .nExp(5)
 6.   .problem ("NQs", "50");
 7. agent.init() ;
 8. agent.info.printAllInfoSolution();
```
El código anterior describe el proceso para la ejecución de una metaheurística a fin de resolver un problema específico, en este caso el problema de las n reinas. En la línea 1 se crea un objeto de tipo agente. En la línea 2 se especifica el tipo de metaheurística utilizada. En la línea 3 se selecciona el archivo donde están los parámetros de dicha metaheurística. En la línea 4 se estipulan el número de evaluaciones que ejecutará el algoritmo, observe que en este caso sería equivalente a definir el criterio de parada del algoritmo. En la línea 5 se define el número de experimentos que se van a ejecutar, es decir, en este caso se va a ejecutar el algoritmo 5 veces con los mismos parámetros. En la línea 6 se define el problema y la instancia del problema a ser abordado, en este caso le estamos indicando al agente que va a resolver el problema de las n reinas con n igual a 50. En la línea 7 se llama a la ejecución del proceso y finalmente en la línea 8 se llama al proceso de impresión de información estadística a fin de determinar el comportamiento del algoritmo.

``` [java]
 1. String[] methods={"Method1","Method1"};
 2. String[] configFiles={"Config1","Config2"};
 3. String[] instances={"25","20"};
 4. int[] nEvalsXIns={6500,4000};
 5. Executing e=new Executing()
 6.   .analysis("file")
 7.   .problem("NQs")
 8.   .nExperiments(5)
 9.   .configFiles(configFiles)
10.   .methods(methods)
11.   .instances(instances)
12.   .nEvalsXInst(nEvalsXIns);
13. e.run();
```
El código anterior describe el proceso para la ejecución de varias metaheurísticas a fin de resolver un problema específico, en este caso el problema de las n reinas. En la línea 1 se establece un arreglo denominado *methods* donde se van a agregar los mnemónicos o siglas asociadas a cada metaheuristica ---es importante aclarar que en este caso el nombre “método1” debe ser substituido, por ejemplo, por SA para el caso del recocido simulado, en la ... se observan las siglas---. En la línea 2 se definen los archivos de configuración de los parámetros de cada una de las metaheurísticas definidas anteriormente. En las líneas 3 y 4 se establecen los vectores dónde se indicarán las instancias del problema y el número de evaluaciones para cada una de ellas. En la línea 5 se instancia la clase *Executing*. Luego, en la línea 6 se establece la posibilidad de que la información sea guardada en un archivo cuyo nombre es *nombre_del_problema fecha  hora.txt* En la línea 7 se indica cuál es el problema que se quiere resolver, en este caso el problema de las n reinas. En la línea 8 se indica el número de experimentos que se van a ejecutar. Finalmente en las líneas 9 a la 12 se cargan en la clase los vectores de configuración definidos en las líneas 1 a la 4. En la línea 13 se llama al método para ejecutar el proceso correspondiente.
 
## Estructura 
La plataforma está compuesta principalmente por 5 directorios, los cuales son : bin, config, instances, output y src. En el directorio *bin* se encuentran los archivos compilados. En el directorio *config* se encuentran tantas carpetas como métodos definidos en el programa. Cada carpeta tiene el nombre del mnemonico o las siglas del algoritmo implementado. Dentro de cada carpeta pueden existir uno o más archivos con extensión *.cfg*, dentro de estos archivos se encuentran los parámetros de ejecución del algoritmo en cuestión. Así por ejemplo ... En el directorio *instances* se encuentran tantas carpetas como problemas definidos en la plataforma. En cada carpeta  se encontrarán diferentes instancias de los problemas definidos. En el directorio *output* se almacenarán los archivos de ejecución del programa. Finalmente, en el directorio *src* se encuentran los archivos fuentes del programa. La estructura de este directorio es la siguiente agent, algorithm, basic, examples, io, landscape, operators problem, state, statistics y util.
1. ...

