# Terminkalender

Es una aplicación que sirve de herramienta para la enseñanza de idiomas a un grupo de alumnos que interactuarán entre si mediante dicha aplicación. Dicha herramienta se presenta como un juego que tendrá un calendario de lunes a domingo y un chat. Los alumnos tendrán diferentes tareas que realizar conjuntamente a lo largo de la semana y deberán hablar entre ellos en el chat, así podrán ponerse de acuerdo entre ellos y anotar esas tareas en el calendario. El método de aprendizaje se basará en el uso del calendario que representa un sistema del tiempo real, del día a día; y un chat con el que se pretende que mediante el sistema de comunicación verbal escrito, el alumno tenga una mayor inmersión en el uso del idioma cotidiano.

## Aplicaciones

Terminkalender se puede dividir en tres aplicaciones distintas:

  * Aplicación del alumno: Permite a los alumnos conectarse a la sesión del juego anteriormente explicada. Esta desarrollada en [LibGDX](https://github.com/libGDX/libGDX) y para la sincronización con el servidor utiliza [WebSockets](https://github.com/TooTallNate/Java-WebSocket)
  * Aplicación del profesor: Esta aplicación permite al profesor crear las sesiones de juego para los alumnos y recopilar los datos generados por la interacción de estos: las conversaciones de chat y la correcta asignación de las tareas en el calendario. También esta desarrollada en [LibGDX](https://github.com/libGDX/libGDX) y para la sincronización con el servidor utiliza [WebSockets](https://github.com/TooTallNate/Java-WebSocket)
  * Servidor: Se encarga de almacenar los datos e interconectar tanto la aplicación del alumno como la del profesor. Desarrollado en PHP y [Rachet](https://github.com/ratchetphp/Ratchet) para crear el servidor de WebSockets.

## Instalar recursos necesarios

Aqui voy a explicar que se necesita para trabajar en tu propio ordenador con Terminkalender (Yo uso Ubuntu):

#### Aplicación del alumno y del profesor

Las dos aplicaciones están implementadas en el mismo proyecto de [LibGDX](https://github.com/libGDX/libGDX). Para modificarlo y ejecutarlo, se puede seguir la guia que proporciona su página en github para usarlo en [Eclipse](https://github.com/libgdx/libgdx/wiki/Gradle-and-Eclipse), [Intellij IDEA](https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA), [NetBeans](https://github.com/libgdx/libgdx/wiki/Gradle-and-NetBeans), o si no se quiere usar un IDE, por  [linea de comandos](https://github.com/libgdx/libgdx/wiki/Gradle-on-the-Commandline). No hay que olvidar que si se usa uno de los IDEs mencionados anteriormente, hay que instalar en ellos las siguientes [dependencias](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-%28Eclipse%2C-Intellij-IDEA%2C-NetBeans%29). Para la construcción del proyecto se usa [Gradle](http://gradle.org/), así que no hay que preocuparse de las dependencias como [WebSockets](https://github.com/TooTallNate/Java-WebSocket), que automaticamente se añaden cuando se construye el proyecto.

#### Servidor

El servidor se encuentra en el directorio [chatServer](https://github.com/javosuher/Terminkalender/tree/master/chatServer), y para hacerlo funcionar se necesitará tener instalado PHP, Apache y MySQL, ya que usa una base de datos para almacenar información. Una guia de instalación para Ubuntu 14.04 sería la [siguiente](https://www.digitalocean.com/community/tutorials/how-to-install-linux-apache-mysql-php-lamp-stack-on-ubuntu-14-04). Para la configuración del usuario de la base de datos, se realiza en el fichero [config.php](https://github.com/javosuher/Terminkalender/blob/master/chatServer/src/MyApp/config.php), se puede dejar el que tengo yo o modificar al que se quiera.

Para instalar las dependecias del servidor se usa [Composer](https://getcomposer.org/), así que suponiendo que ya tenemos descargado el fichero composer.phar en nuestro directorio de usuario, se instalarían con el siguiente comando estando situados en [chatServer](https://github.com/javosuher/Terminkalender/tree/master/chatServer):

```
php ~/composer.phar install
```
Por último, para ejecutar el servidor en nuestro PC, estando de nuevo en la carpeta raíz del servidor, se introduce el siguiente comando:

```
php server.php
```
