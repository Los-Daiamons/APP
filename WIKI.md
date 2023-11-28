PROTOCOL D'ACCIONS
1. Conexión al Servidor (Raspberry Pi):

    URL del Servidor WebSocket:
        La aplicación Android establece la conexión WebSocket con la dirección IP o la URL del servidor WebSocket en la Raspberry Pi. En este caso: ws://192.168.0.27:8887.

2. Acciones Específicas:

    Acción: "enviar_mensaje":
        La Raspberry Pi recibe un mensaje identificado con la acción "enviar_mensaje" y procesa el contenido del mensaje, por ejemplo, mostrándolo en un dispositivo conectado a la Raspberry Pi o realizando una acción específica basada en el mensaje recibido.

Implementación en la Aplicación Android:
1. Conexión al Servidor:

    Establecer Conexión WebSocket:
        La aplicación Android crea una instancia de WebSocket y se conecta al servidor WebSocket en la Raspberry Pi utilizando la dirección y el puerto correctos.

2. Envío de Mensajes:

    Enviar Mensajes al Servidor:
        Cuando el usuario interactúa con la interfaz de la aplicación, se crea un mensaje estructurado según el protocolo definido y se envía al servidor WebSocket.
