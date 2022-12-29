// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
import { Big } from "big.js";

// BEGIN EXTRA CODE
// END EXTRA CODE

/**
 * @param {string} classNameToSearch
 * @param {string} classNameToSet
 * @returns {Promise.<void>}
 */
export async function ToggleClassOnElement(classNameToSearch, classNameToSet) {
	// BEGIN USER CODE
	if (!classNameToSearch && classNameToSearch.trim() != "") {
		return Promise.reject('classNameToSearch is not set');
	}
	if (!classNameToSet  && classNameToSet.trim() != "") {
		return Promise.reject('classNameToSet is not set');
	}

	var el = document.querySelector('.' + classNameToSearch);
	if (el) {
		el.classList.toggle(classNameToSet);
		return Promise.resolve(true);
	}
	else{
		return Promise.resolve(false);
	}
	resolve();
	// END USER CODE
}
