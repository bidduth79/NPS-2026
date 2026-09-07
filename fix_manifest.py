with open('app/src/main/AndroidManifest.xml', 'r') as f:
    content = f.read()

# Add permissions
permissions = """
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
"""
content = content.replace('<uses-permission android:name="android.permission.INTERNET" />', permissions)

# Add receiver
receiver = """
        <receiver android:name=".receivers.SmsReceiver" android:exported="true" android:permission="android.permission.BROADCAST_SMS">
            <intent-filter android:priority="999">
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>
"""
content = content.replace('</application>', receiver + '    </application>')

with open('app/src/main/AndroidManifest.xml', 'w') as f:
    f.write(content)
