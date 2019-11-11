import com.opensymphony.workflow.InvalidInputException
import com.atlassian.jira.component.ComponentAccessor
cfParent = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11413")
selectedEquipment = transientVars["issue"].getCustomFieldValue(cfParent) as String[]

if (selectedEquipment.find { it == 'Телефонный аппарат' }) {
  if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11217")) == null) {
    throw new InvalidInputException("customfield_11217", "Укажите тип заявки.")
  }
  if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11401")) == null) {
    throw new InvalidInputException("customfield_11401", "Укажите модель телефона.")
  }
}
if (selectedEquipment.find { it == 'Модем' }) {
    if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11405")) <= 0) {
        throw new InvalidInputException("customfield_11405", "Укажите количество модемов.")
    }
}
if (selectedEquipment.find { it == 'SIM' }) {
    if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11406")) <= 0) {
        throw new InvalidInputException("customfield_11406", "Укажите количество SIM карт.")
    }
    if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11407")) == null) {
        throw new InvalidInputException("customfield_11407", "Укажите тип SIM карты.")
    }
}
if (selectedEquipment.find { it == 'Bluetooth' }) {
    if (transientVars["issue"].getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_11410")) <= 0) {
        throw new InvalidInputException("customfield_11410", "Укажите количество Bluetooth устройств.")
    }
}
