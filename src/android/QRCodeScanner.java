package cordova-plugin-QRCodeScanner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class QRCodeScanner extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getNextAppointment")) {
            //String message = args.getString(0);
            //this.coolMethod(message, callbackContext);
			int minutes = args.getInt(0);
			//this.getNextAppointment(minutes,callbackContext)
		Intent intent = new Intent();
        intent.setAction("com.ril.rposcentral.RPOSHomeActivity");
        Bundle bundle = new Bundle();
        bundle.putString("DATA", getData());
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
            return true;
        }
        return false;
    }
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("Response");
        String strMessage = "STATUS : " + data.getBooleanExtra("SUCCESS", false) + "\nREASON : " + data.getStringExtra("REASON")
                + "\nMOP : " + data.getStringExtra("MOP");
        builder.setMessage(strMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private String getData() {
        String userid = "10048428", password = "Rjil@5432", ORN = "NO000000675K";
        StringBuilder sb = new StringBuilder();
        sb.append("<TxnInfo>");
        sb.append("<TxnHeader>");
//User id and password required for login
        sb.append("<UserID>" + userid + "</UserID>");
        sb.append("<Password>" + password + "</Password>");
        sb.append("<PartialPayment>" + 0 + "</PartialPayment>");
        sb.append("<OrderType>" + "</OrderType>");
        sb.append("<TxnTotal>" + 500 + "</TxnTotal>");
        sb.append("<ORN>" + ORN + "</ORN>");
        sb.append("<TransactionType>" + "</TransactionType>");
//Cutomer detals start
        sb.append("<EmailID>" + "</EmailID>");
        sb.append("<ContactNumber>" + "</ContactNumber>");
        sb.append("<CustomerName>" + "</CustomerName>");
        sb.append("<FlatNo>" + "</FlatNo>");
        sb.append("<FloorNo>" + "</FloorNo>");
        sb.append("<BlockNo>" + "</BlockNo>");
        sb.append("<BuildingName > " + " </BuildingName>");
        sb.append("<SocietyName > " + " </SocietyName>");
        sb.append("<PlotNo > " + " </PlotNo>");
        sb.append("<Street > " + " </Street>");
        sb.append("<Sector > " + " </Sector>");
        sb.append("<Area > " + " </Area>");
        sb.append("<City > " + " </City>");
        sb.append("<State > " + " </State>");
        sb.append("<Pincode > + </Pincode>");
//Customer details end
        sb.append("</TxnHeader>");
        sb.append("</TxnInfo>");
        return sb.toString();
    }
    private void getNextAppointment(int minutes, CallbackContext callbackContext) {
        if ( minutes > 0) {
			if(foundAppointment){
				JSONObject returnObject =  new JSONObject();
				returnObject.put("title", appointmentTitle);
				returnObject.put("title", appointmentDate);
				callbackContext.success(returnObject);
			} else{
				callbackContext.success("");
			}
        } else {
            callbackContext.error("minutes must be > 0");
        }
    }
}
