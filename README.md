Como se puede observar en la siguiente imagen:
Foto1
El promp le envia una lista de preguntas frecuentes (que es una clase local) con la que a partir de ella es capaz de generar respuestas.
Además de un par de reglas para que envie lo que el cliente espera y así evitar posibles errores

Aqui preparamos el mensaje y despues con el retrofit lo enviamos
Foto2

Una vez que hacemos esto se envia una peticion al LLM con la que esperamos:
    -Respuesta: basicamente lo que vera el usuarios
    -Posibles Preguntas: basicamente genera posbles cosas que le podria interesar al cliente

Una vez recibido se añadira el texto de la respuesta, además de que se escribira el texto con las posibles preguntas que le pueden interesar al usuario
Foto3

Para comprobar que de verdad lo estamos enviando al LLM que se nos proporciona lo veremos desde el logcat
Foto4

Como se puede observar si que se hace la peticion