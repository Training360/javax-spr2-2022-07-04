window.onload = function() {

        const socket = new SockJS('/websocket-endpoint');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            stompClient.subscribe("/topic/employees", function(message) {
                const response = JSON.parse(message.body)
                const text = response.text
                 document.querySelector("#message-div").innerHTML += "<p>" + text + "</p>"
            })
        });

        document.querySelector("#message-button").onclick = function() {
            const content = document.querySelector("#message-input").value;
            stompClient.send("/app/messages", {}, JSON.stringify({"content": content}))
        };

}