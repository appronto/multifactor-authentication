// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
import "mx-global";
import { Big } from "big.js";

// BEGIN EXTRA CODE
// END EXTRA CODE

/**
 * If the user is logged in, logs out the user and restarts the client even when it's a guest session
 * 
 * If the user is not logged in, the return value is false
 * 
 * @returns {Promise.<boolean>}
 */
export async function SignOut_Custom() {
	// BEGIN USER CODE
    mx.logout();
    return Promise.resolve(true);
	// END USER CODE
}
