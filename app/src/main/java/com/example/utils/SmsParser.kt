package com.example.utils

import com.example.data.TransactionEntity

object SmsParser {
    fun parseSms(sender: String, message: String, timestamp: Long): TransactionEntity? {
        val senderUpper = sender.uppercase()
        // Identify Medium based on Sender ID or Name
        val medium = when {
            senderUpper.contains("BKASH") -> "bKash"
            senderUpper.contains("NAGAD") -> "Nagad"
            senderUpper.contains("UPAY") -> "Upay"
            senderUpper.contains("ROCKET") || senderUpper.contains("16216") -> "Rocket"
            senderUpper.contains("DBBL") || senderUpper.contains("DUTCH") -> "DBBL"
            senderUpper.contains("IBBL") || senderUpper.contains("ISLAMI") -> "Islami Bank"
            senderUpper.contains("BRAC") -> "BRAC Bank"
            senderUpper.contains("CITY") -> "City Bank"
            senderUpper.contains("EBL") -> "EBL"
            senderUpper.contains("MTB") -> "MTB"
            senderUpper.contains("SHIMANTO") || senderUpper.contains("SIMANTO") -> "Shimanto Bank"
            senderUpper.contains("SONALI") -> "Sonali Bank"
            senderUpper.contains("JANATA") -> "Janata Bank"
            else -> return null // Not a recognized financial institution
        }

        // Extract Amount: Matches Tk, Tk., BDT, BDT., followed by optional space, then digits with commas and optional decimals.
        val amountRegex = Regex("""(?:Tk\.?|BDT\.?)\s*([\d,]+(?:\.\d+)?)""", RegexOption.IGNORE_CASE)
        val match = amountRegex.find(message)
        if (match == null) return null
        
        val amountStr = match.groupValues[1].replace(",", "")
        val amount = amountStr.toDoubleOrNull() ?: return null

        // Extract Type (IN or OUT) based on keywords in the message
        val msgLower = message.lowercase()

        // Ignore EFT messages as requested
        if (msgLower.contains("eft")) return null
        
        val type = when {
            msgLower.contains("cash in") || msgLower.contains("received") || 
            msgLower.contains("credited") || msgLower.contains("deposit") || 
            msgLower.contains("add money") || msgLower.contains("remittance") ||
            msgLower.contains("salary") || msgLower.contains("disbursement") -> "IN"
            
            msgLower.contains("cash out") || msgLower.contains("send money") || 
            msgLower.contains("payment") || msgLower.contains("debited") || 
            msgLower.contains("charge") || msgLower.contains("mobile recharge") || 
            msgLower.contains("transfer") || msgLower.contains("purchase") || 
            msgLower.contains("withdrawal") || msgLower.contains("purchased") -> "OUT"
            
            else -> return null // Unknown transaction type
        }

        return TransactionEntity(
            amount = amount,
            type = type,
            medium = medium,
            date = timestamp,
            rawMessage = message
        )
    }
}
