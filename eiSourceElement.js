function setSourceElementOnEI() {
	var innerHTMLEIBODY = document.getElementsByTagName('body')[0].innerHTML;
	var myDialogEISourceElement = '<h1>text edit source</h1>';
	var elmEIDSS = document.getElementsByTagName("*");
	document.getElementsByTagName('body')[0].innerHTML = innerHTMLEIBODY + '<h1 id="eiTextDS"></h1>';
	for(var i = 0, all = elmEIDSS.length; i < all; i++){ 
		if (elmEIDSS[i].id != '' || elmEIDSS[i].id != null) {
			var randomClassValueEIDS = Math.floor(Math.random() * 999999);
		    elmEIDSS[i].classList.add('eiTagIdentify' + randomClassValueEIDS);

		    var clickEISourceElement = function() {
		    	try {
		    		prompt("Source", document.getElementById(this.id).innerHTML);
		    	} catch(Exception e) {}
			    return false;
			}
		    document.getElementsByClassName('eiTagIdentify' + randomClassValueEIDS)[0].onclick = clickEISourceElement;
		}
	}
}

function setSourceElementOffEI() {
	var elmEIDSS = document.getElementsByTagName("*");
	for(var i = 0, all = elmEIDSS.length; i < all; i++){ 
        elmEIDSS[i].classList.remove('eiTagIdentify');
	    elmEIDSS[i].onclick = '';
	}
}