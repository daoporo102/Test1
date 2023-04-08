package com.example.test1

import android.app.Dialog
import android.mtp.MtpStorageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.test1.databinding.ActivitySaveUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ActivitySaveUserProfile : AppCompatActivity() {

    private lateinit var binding: ActivitySaveUserProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_save_user_profile)
        binding = ActivitySaveUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {

            showProgressBar()

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()

            val user = User(firstName, lastName, bio)
            if (uid != null) {
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        uploadProfilePic()
                    } else {
                        hideProgressBar()
                        Toast.makeText(
                            this@ActivitySaveUserProfile,
                            "Failed to update profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        storageReference =
            FirebaseStorage.getInstance().getReference("Users/" + firebaseAuth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener {
           hideProgressBar()
            Toast.makeText(
                this@ActivitySaveUserProfile,
                "Profile successfully updated",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(
                this@ActivitySaveUserProfile,
                "Failed to upload the image ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun showProgressBar() {
        dialog = Dialog(this@ActivitySaveUserProfile)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()

    }

}