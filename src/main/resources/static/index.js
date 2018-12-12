$(function () {
    // send command on enter
    $('#command').keypress(function (event) {
        if (event.keyCode === 13) {
            $('#send_command').click();
        }
    });
    // send command on click 'Send'
    $('#send_command').click(executeCommand);
});

// parse command to array of commands ({command: 'singleCommand'}[])
function parseCommands(commandString) {
    return commandString.split(';').map(c => c.trim()).filter(c => c).map(c => ({command: c}));
}

// eg: 'address 1' -> 'left 1 10' -> 'top 1 ; wait 4s; down 1'
function executeCommand(event) {
    event.preventDefault();

    const command = $('#command');
    const errmsg = $('#errmsg');
    command.attr('disabled', 'disabled');

    if (command.val().trim() !== '') {
        const request = new XMLHttpRequest();
        const url = '/app/visca';
        const params = JSON.stringify({
            'commands': parseCommands(command.val())
        });

        request.open("POST", url, true);
        request.setRequestHeader("Content-type", "application/json; charset=utf-8");
        request.onload = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                // print response
                printResponse(command.val(), JSON.parse(this.responseText).responseMessage);
                command.val('');
                errmsg.text(null);
            } else {
                // print error
                const errorMessage = JSON.parse(this.responseText).errorMessage;
                errmsg.text(errorMessage);
            }
            command.removeAttr('disabled');
        };
        request.send(params);
    } else {
        errmsg.text('Please enter command.');
        command.removeAttr('disabled');
    }
}

function printResponse(command, responseMessage) {
    const responseContainer = $(document.createElement('div')).addClass('element-container response-container');
    const time = $(document.createElement('div')).addClass('response-time').text(new Date().toLocaleString());
    const executedCommand = $(document.createElement('div')).addClass('command-text response-command').text(command);

    const responseMessages = responseMessage.split('<>').map(msg =>
        $(document.createElement('div')).text(msg).append($(document.createElement('br')))
    );
    const responseBody = $(document.createElement('div')).addClass('response-body');
    responseBody.append(responseMessages);

    responseContainer.append([time, executedCommand, responseBody]);
    $('#responses_list').prepend(responseContainer);
}