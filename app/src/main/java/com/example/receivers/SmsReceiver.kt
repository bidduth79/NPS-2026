package com.example.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.data.AppDatabase
import com.example.utils.SmsParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val db = AppDatabase.getDatabase(context).transactionDao()
            
            CoroutineScope(Dispatchers.IO).launch {
                for (sms in messages) {
                    val sender = sms.displayOriginatingAddress ?: continue
                    val body = sms.displayMessageBody ?: continue
                    val timestamp = sms.timestampMillis
                    
                    val transaction = SmsParser.parseSms(sender, body, timestamp)
                    if (transaction != null) {
                        db.insertTransaction(transaction)
                    }
                }
            }
        }
    }
}
