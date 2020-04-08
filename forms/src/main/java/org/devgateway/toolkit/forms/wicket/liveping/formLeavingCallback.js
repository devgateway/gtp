//invoked before the window is unloaded
//we display a confirmation box, if the box is OK, we allow the user
//to leave the page and at the same time unlock editing
//browser leaves the tab

function unlockForm() {
	$.ajax({
		type: 'POST',
		async: false,
		url: '${url}',
		data: '${args}'
	});
}

disableUnlockFormOnUnload = function () {
	$(window).off('unload', unlockForm);
};

$(window).on('unload', unlockForm);
