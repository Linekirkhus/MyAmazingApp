package com.example.myamazingapp


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myamazingapp.util.FirestoreUtil
import com.example.myamazingapp.util.StorageUtil
import kotlinx.android.synthetic.main.activity_create_profile.view.*
import kotlinx.android.synthetic.main.fragment_upload_photo.*
import java.io.ByteArrayOutputStream

class UploadPhotoFragment : Fragment() {

    private final val SELECT_IMAGE_REQUEST_CODE: Int = 3
    private lateinit var selectedImageBytes: ByteArray
    private var photoHasChanged: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_photo, container, false)
        view.apply {
            btn_upp_fragment_select_photo.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(
                        Intent.EXTRA_MIME_TYPES,
                        arrayOf(
                            "image/jpeg",
                            "image/png",
                            "image/jpg",
                            "image/tif",
                            "image/gif",
                            "image/pdf",
                            "image/psd",
                            "image/txt"
                        )
                    )
                }
                startActivityForResult(
                    Intent.createChooser(intent, "Please select.."),
                    SELECT_IMAGE_REQUEST_CODE
                )
            }
            btn_upp_fragment_save_update.setOnClickListener {
                if(::selectedImageBytes.isInitialized)
                    StorageUtil.uploadProfilePhoto(selectedImageBytes){ imagePath ->
                        FirestoreUtil.updateCurrentUser(
                                et_cp_user_name.text.toString(),
                                et_cp_user_bio.text.toString(),
                                imagePath)
                    }
                else
                    FirestoreUtil.updateCurrentUser(
                            et_cp_user_name.text.toString(),
                            et_cp_user_bio.text.toString(),
                           null)
            }
        //    btn_sign_out
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {
            val selectedImagePath = data.data
            val selectedImageBitmap = MediaStore.Images.Media
                .getBitmap(activity?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()
            //   if (selectedImagePath != null) {
            //   uploadImageToFirebase(selectedImagePath)
            // TODO Load Photo
        }
    }
}


