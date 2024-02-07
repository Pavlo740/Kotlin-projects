import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Вихід")
        builder.setMessage("Ви впевнені, що хочете вийти з додатку?")
        builder.setPositiveButton("Так") { dialogInterface: DialogInterface, i: Int ->
            finishAffinity() // Завершуємо всі активності
        }
        builder.setNegativeButton("Ні") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss() // Закриваємо діалог
        }
        builder.show()
    }
}
