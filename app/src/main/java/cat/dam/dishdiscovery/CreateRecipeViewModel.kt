package cat.dam.dishdiscovery

import androidx.lifecycle.ViewModel
import cat.dam.dishdiscovery.objects.Ingridient

class CreateRecipeViewModel : ViewModel(){
    var ingMes = mutableMapOf<Ingridient, Mesurement>()
}