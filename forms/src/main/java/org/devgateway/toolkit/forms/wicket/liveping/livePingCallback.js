// global variable that prevents live pinging while auto-save is in progress
autoSaveInFlight = false;

//pings the wicket behavior every 30 seconds, only when the client is active and not idle
ifvisible.onEvery(30, function(){
	${callbackScript}
});

var confirmationWasEnabled;

function livePingBeforeSend() {
	confirmationWasEnabled = isFormLeavingConfirmationEnabled();
	disableFormLeavingConfirmation();
}

function livePingDone() {
	if (confirmationWasEnabled) {
		enableFormLeavingConfirmation();
	}
}
