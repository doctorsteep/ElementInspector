# Element Inspector


## EI Content editable - [eiContentEditable.js](eiContentEditable.js)
```javascript
function setContenteditableOnEI() { // On content editable
	document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'true');
}
function setContenteditableOffEI() { // Off content editable
	document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'null');
}
// Powered by DoctorSteep - from project ElementInspector
```
> Редактирование елементов простым способом а именно изменение его как текст. **_setContenteditableOnEI()_** - `Включает редактирование`, **_setContenteditableOffEI()_** - `Выключает редактирование`


## EI Highlight border - [eiHighlightBorder.js](eiHighlightBorder.js)
```javascript
function setSourceElementOnEI() {
	var bodyInnerElement = document.getElementsByTagName('body')[0].innerHTML;
	document.getElementsByTagName('body')[0].innerHTML = bodyInnerElement + 
	'<style type="text/css">.eiTagBorderDS {box-shadow: 0 0 4px rgba(0,0,0,.2);} .eiTagBorderDS:hover {cursor: pointer; box-shadow: 0 2px 7px rgba(0,0,0,.4);}</style>' + 
	'';

	var els = document.getElementsByTagName("*");
	for(var i = 0, all = els.length; i < all; i++){ 
	    els[i].classList.add('eiTagBorderDS');
	}
}

function setSourceElementOffEI() {
	var els = document.getElementsByTagName("*");
	for(var i = 0, all = els.length; i < all; i++){ 
	    els[i].classList.remove('eiTagBorderDS');
	}
}
// Powered by DoctorSteep - from project ElementInspector
```
> Подсветка границ элементов. **_setSourceElementOnEI()_** - `Включает подсветку`, **_setSourceElementOffEI()_** - `Выключает подсветку`

## EI Page source - [eiPageSource.js](eiPageSource.js)
```javascript
function setEditSourcePageEI() {
	var bodyInner = document.getElementsByTagName('body')[0].innerHTML;
	var htmlInner = document.getElementsByTagName('html')[0].innerHTML;
	document.getElementsByTagName('body')[0].innerHTML = bodyInner + 
	'<style id="styleEIDS1" type="text/css">div.eiAlertDS {display: flex;position: fixed;top: 0;bottom: 0;left: 0;right: 0;background-color: rgba(0,0,0,.3); z-index: 999;align-items: center;justify-content: center;}</style>' + 
	'<style id="styleEIDS2" type="text/css">div.eiAlertContent {display: block;background-color: rgba(255,255,255,1);width: 600px;height: auto;z-index: 1000;box-shadow: 0 2px 4px rgba(0,0,0,.6);}</style>' + 
	'<style id="styleEIDS3" type="text/css">textarea.eiTextareaDS {display: block;width: 590px;height: 70vh;resize: none;color: rgba(0,0,0,.9);font-size: 13px;outline: none;border: none;margin: 0;margin-left: 10px;padding: 0;}</style>' + 
	'<style id="styleEIDS4" type="text/css">h2.eiAlertTitle {font-family: sans-serif;font-weight: 600;font-size: 15px;color: rgba(0,0,0,1);outline: none;border: none;background-color: rgba(0,0,0,.1);margin: 0;padding: 10px;user-select: none;}</style>' + 
	'<style id="styleEIDS5" type="text/css">div.eiBottomContent {width: 100%;padding: 0;margin: 0;display: flex;align-items: center;}</style>' + 
	'<style id="styleEIDS6" type="text/css">button.buttonEIAlert {background-color: rgba(0,0,0,.1);color: rgba(0,0,0,.8);border-radius: 2px;padding: 7px;margin: 8px;user-select: none;outline: unset;border: 1px solid rgba(0,0,0,.15);}button.buttonEIAlert:active {box-shadow: 0 2px 3px rgba(0,0,0,.3);}</style>' + 
	'<div class="eiAlertDS" id="eiAlertDS">' + 
		'<div class="eiAlertContent">' + 
			'<h2 class="eiAlertTitle">Source code page</h2>' + 
			'<textarea class="eiTextareaDS" id="eiTextareaDS">' + htmlInner + '</textarea>' + 
			'<div class="eiBottomContent">' + 
				'<button class="buttonEIAlert" id="save" onclick="document.getElementsByTagName(\'html\')[0].innerHTML = document.getElementById(\'eiTextareaDS\').value">Save</button>' + 
				'<button class="buttonEIAlert" id="close" onclick="document.getElementById(\'eiAlertDS\').remove()">Close</button>' + 
			'</div>' + 
		'</div>' + 
	'</div>' + 
	'';
}
// Powered by DoctorSteep - from project ElementInspector
```
> Просмотр кода страницы и его редактирование. **_setEditSourcePageEI()_** - `Открывает диалог с редактированием исходного кода страницы`

[*СКАЧАТЬ ПРИЛОЖЕНИЕ*](https://play.google.com/store/apps/details?id=com.doctorsteep.elementinspector)
