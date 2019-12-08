const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();
 
	exports.updateCreateUser = functions.firestore
     .document('users/{userId}')
	 .onWrite((change, context) => {
	   const newValue = change.after.data();
	   const name = newValue.name;
	   console.log(name);
	   const payLoad = {
        data:{   //notification
            title: name
        }
    };
 return admin.messaging().sendToTopic("Message_Notifications", payLoad);
	 
     });
	
    