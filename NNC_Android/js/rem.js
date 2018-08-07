//(function(){
//  var html = document.documentElement;
//  var hWidth = html.clientWidth;
//  alert(hWidth);
//  html.style.fontSize = hWidth/15 + 'px';
//})();
(function (doc, win) {
  var docEl = doc.documentElement,
    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
    recalc = function () {
      var clientWidth = docEl.clientWidth;
      var clientHeight = docEl.clientHeight;
// 		console.log("浏览器宽度："+clientWidth+"浏览器高度："+clientHeight);
      if (!clientWidth) return;
      docEl.style.fontSize = 16 * (clientWidth / 320) + 'px';
    };
 
  if (!doc.addEventListener) return;
  win.addEventListener(resizeEvt, recalc, false);
  doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);