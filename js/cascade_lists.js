$("#customfield_11646").hide()
$("#customfield_11646").parent('div').children("label").append('<span class="aui-icon icon-required">Required</span>');
$("#customfield_11646").parent('div').children("label").after(`<select class="select cf-select" name="customfield_11646_city" id="customfield_11646_city" style="width: 27%; display: inline-block;" placeholder="Город"><option selected="true" disabled>Выберите город</option></select><select class="select cf-select" name="customfield_11646_address" id="customfield_11646_address" style="width: 47%; display: inline-block; margin-left: 1%;"><option selected="true" disabled>Выберите адрес</option></select><input class="textfield text" id="customfield_11646_room" name="customfield_11646_room" style="width: 6%; display: inline-block; margin-left: 1%;" maxlength="6" type="text" value="">`)
$("#customfield_11646").parent('div').append(`<div class="checkbox"><input class="checkbox" id="customfield_11646_checkbox" name="customfield_11646_checkbox" type="checkbox" value="18785"><label for="customfield_11646_checkbox">Моего адреса нет в списке</label></div>`)
var addresses
AJS.$.ajax({
  url: AJS.contextPath() + '/rest/my-groovy/latest/custom/getAddressesJson',
  async: false,
  success: function(data) {
    addresses = data
  }
});
$.each(addresses, function(index, item) {
  $("#customfield_11646_city").append(`<option value="` + item.city + `">` + item.city + `</option>`)
});
$('#customfield_11646_city').change(function() {
  var city = $(this).val()
  if (city == "Выберите город") {
    var html = '<option selected="true" disabled="">Выберите адрес</option>'
  } else {
    var lcns = addresses.find(x => x.city === city).addresses
    var html = $.map(lcns, function(lcn) {
      return '<option value="' + lcn + '">' + lcn + '</option>'
    }).join('');
    $("#customfield_11646_address").html(html)
    var address = $('#customfield_11646_address').val()
    $("#customfield_11646").val(city + " " + address)
  }
});
$('#customfield_11646_address').change(function() {
  var city = $('#customfield_11646_city').val()
  var address = $(this).val()
  $("#customfield_11646").val(city + " " + address)
});
$('#customfield_11646_room').change(function() {
  var city = $('#customfield_11646_city').val()
  var address = $('#customfield_11646_address').val()
  var room = $('#customfield_11646_room').val()
  $("#customfield_11646").val(city + " " + address + " - " + room)
});

$("#customfield_11646_checkbox, label[for=customfield_11646_checkbox]").click(function() {
  if ($("#customfield_11646_checkbox").is(":checked")) {
    $("#customfield_11646").val("")
    $("#customfield_11646").show()
    $("#customfield_11646_address").html('<option selected="true" disabled="">Выберите адрес</option>')
    $("#customfield_11646_city").val("Выберите город").change()
    $("#customfield_11646_city").hide()
    $("#customfield_11646_address").hide()
    $("#customfield_11646_room").val("")
    $("#customfield_11646_room").hide()
  } else {
    $("#customfield_11646").hide()
    $("#customfield_11646").val("")
    $("#customfield_11646_city").show()
    $("#customfield_11646_address").show()
    $("#customfield_11646_room").show()
  }
});
