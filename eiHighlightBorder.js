function setSourceElementOnEI() {
	var bodyInnerElement = document.getElementsByTagName('body')[0].innerHTML;
	document.getElementsByTagName('body')[0].innerHTML = bodyInnerElement + 
	'<style type="text/css">.eiTagBorderDS {border: 1px dashed rgba(0,0,0,.4);background-color:transparent;}.eiTagBorderDS:hover {cursor: pointer; box-shadow: 0 2px 7px rgba(0,0,0,.4);}</style>' + 
	'';

	var els = document.getElementsByTagName("*");
	for(var i = 0, all = els.length; i < all; i++){ 
	    els[i].classList.add('eiTagBorderDS');
	    els[i].click = function() {alert(this.innerHTML); return this;};
	    els[i].title = els[i].id + ' / Click to edit element';
	}
}

function setSourceElementOffEI() {
	var els = document.getElementsByTagName("*");
	for(var i = 0, all = els.length; i < all; i++){ 
	    els[i].classList.remove('eiTagBorderDS');
	    els[i].onclick = '';
	}
}