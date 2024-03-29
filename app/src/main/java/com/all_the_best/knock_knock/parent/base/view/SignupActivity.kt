package com.all_the_best.knock_knock.parent.base.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.databinding.ActivitySignupBinding
import com.all_the_best.knock_knock.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        StatusBarUtil.setStatusBar(this, resources.getColor(R.color.blue_status_bar, null))

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        binding.txtPlusChild = "+자녀추가"
        binding.txtSignUp = "회원가입"
        binding.txtLogin = "로그인"

        binding.signupBtnAddInfant.setOnClickListener {
            binding.signupLinearInfant2.visibility = View.VISIBLE
        }


        val intent = Intent(this, LoginActivity::class.java)
        signup_btn_signup.setOnClickListener{
            val id = signup_edt_id.text.toString()
            val pw = signup_edt_pw.text.toString()

            if (id != "" && pw != ""){
                Toast.makeText(this@SignupActivity, "반갑습니다, "+id+"님! 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                intent.putExtra("id", id)
                intent.putExtra("pw", pw)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
    }
}