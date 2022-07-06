const evtSource = new EventSource("api/events");
evtSource.addEventListener("event",
    function(event) {
        const text = JSON.parse(event.data).text;
        console.log(text);
        document.querySelector("#message-div").innerHTML +=
            `<p>${text}</p>`;
    });