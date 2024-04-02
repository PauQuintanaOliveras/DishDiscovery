package cat.dam.dishdiscovery.objects

import com.google.firebase.firestore.PropertyName

class IngridientQty {
        @PropertyName("Ingridient")
        var ingridient: String = ""
        @PropertyName("Mesurement")
        var mesurement: String = ""
        @PropertyName("Qty")
        var qty: Float = 0f
}