(function(doc, win) {
	var docEl = doc.documentElement,
		resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
		recalc = function() {
			var clientWidth = docEl.clientWidth;
			//    alert(clientWidth);
			if(!clientWidth) return;
			docEl.style.fontSize = 16 * (clientWidth / 0.92 / 320) + 'px';
		};

	if(!doc.addEventListener) return;
	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

function clearBox(txt) {
	$('textarea').val(txt);
	$('.image-item-add').remove();
	$('.image-item-base').show();
}