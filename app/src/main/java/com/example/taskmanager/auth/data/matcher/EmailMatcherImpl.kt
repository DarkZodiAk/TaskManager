package com.example.taskmanager.auth.data.matcher

import android.util.Patterns
import com.example.taskmanager.auth.domain.matcher.EmailMatcher
import javax.inject.Inject

class EmailMatcherImpl @Inject constructor(): EmailMatcher {
    override fun matches(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}