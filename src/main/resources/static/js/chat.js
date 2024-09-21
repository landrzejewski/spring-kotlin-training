$(() => {

    const clientId = crypto.randomUUID();
    const privateClientId = crypto.randomUUID();
    const messages = $('#messages');
    const recipients = $('#recipients');

    let stompClient = null;

    function updateView(isConnected) {
        $('#username').prop('disabled', isConnected);
        $('#recipients').prop('disabled', !isConnected);
        $('#connectBtn').prop('disabled', isConnected);
        $('#disconnectBtn').prop('disabled', !isConnected);
        $('#sendBtn').prop('disabled', !isConnected);
        $('#message').prop('disabled', !isConnected);
        $('#recipient').prop('disabled', !isConnected);
        if (isConnected) {
            $('#messages').text('');
        }
    }

    function getUsername() {
        return $('#username').val();
    }

    function getMessageText() {
        return $('#message').val();
    }

    function getRecipients() {
        return $('#recipients').val();
    }

    function connect() {
        const username = getUsername()
        const socket = SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({username, clientId, privateClientId}, onConnected);
    }

    function onConnected() {
        updateView(true);
        stompClient.subscribe('/main', onMessage);
        stompClient.subscribe('/private-' + privateClientId, onMessage);
        stompClient.subscribe('/system', onSystemMessage);
    }

    function disconnect() {
        stompClient.disconnect();
        updateView(false);
    }

    function send() {
        const message = {
          sender: getUsername(),
          recipients: getRecipients(),
          text: getMessageText()
        };
        stompClient.send('/ws/chat', {}, JSON.stringify(message));
    }

    function onMessage(socketMessage) {
        const message = JSON.parse(socketMessage.body);
        const timestamp = new Date(message.timestamp).toLocaleTimeString();
        $(`<p>${timestamp} ${message.sender}: ${message.text}</p>`).appendTo(messages);
    }

    function onSystemMessage(socketMessage) {
        const userInfos = JSON.parse(socketMessage.body);
        recipients.empty();
        userInfos
            .filter(userInfo => userInfo.clientId !== clientId)
            .forEach(userInfo => $(`<option value="${userInfo.clientId}">${userInfo.username} (${userInfo.clientId})</option>`).appendTo(recipients));
    }

    function changeVisibility() {
        if($(this).is(':checked')) {

        } else {

        }
    }

    updateView(false);

    $('#connectBtn').click(connect);
    $('#disconnectBtn').click(disconnect);
    $('#sendBtn').click(send);
    $('#isVisibleBtn').change(changeVisibility);

});