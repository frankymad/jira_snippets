$(function() {
  window.rows = 1;
});

$("#customfield_11838").parent('div').children("label").after(`<select class="select cf-select" name="customfield_11838_tech" id="customfield_11838_tech" style="width: 80%; display: inline-block;" placeholder="Выберите оборудование"></select>`)
$("#customfield_11838").hide()
$("#customfield_11838_tech").after('<svg width="28" height="28" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" id="customButtonAddEquip" margin-left="3px"><g fill="none" fill-rule="evenodd"><path d="M0 2.002A2 2 0 0 1 2.002 0h11.996A2 2 0 0 1 16 2.002v11.996A2 2 0 0 1 13.998 16H2.002A2 2 0 0 1 0 13.998V2.002z" fill="#36B37E"></path><path d="M9 12.5a1 1 0 0 1-2 0v-9a1 1 0 1 1 2 0v9z" fill="#FFF" fill-rule="nonzero"></path><path d="M3.5 9a1 1 0 1 1 0-2h9a1 1 0 0 1 0 2h-9z" fill="#FFF" fill-rule="nonzero"></path></g><a xmlns="http://www.w3.org/2000/svg" id="anchor" xlink:href="#" xmlns:xlink="http://www.w3.org/1999/xlink" target="_top"><rect x="0" y="0" width="100%" height="100%" fill-opacity="0"></rect></a></svg>')

$("#customfield_11842").before('<table id="customfield_11842_tech" cellspacing="2" border="0" cellpadding="5" width="80%"><tbody><tr><td>123</td></tr></tbody></table>')
$("#customfield_11842").hide()

AJS.$.ajax({
  url: AJS.contextPath() + '/rest/my-groovy/latest/custom/getSportEquipment',
  async: false,
  success: function(data) {
    sportEquipment = data
  }
});

if (typeof(sportEquipment) != 'undefined'){
  $.each(sportEquipment, function(index, item) {
    $("select[id=customfield_11838_tech]").append(`<option value="` + item[0] + `" index="` + index + `">` + item[0] + ` - ` + item[2] + ` ` + item[1] + `</option>`)
  });
}

$("#customButtonAddRows").click(function() {
  rows = rows + 1;
  $("#customTableField").append(`<tr id="customfield_11202_ct` + rows + `">
  <td>
    <input class="textfield text long-field" id="customfield_11202_` + rows + `_ct1" name="customfield_11202_` + rows + `_ct1" maxlength="254" type="text" value="" placeholder="Пусто">
  </td>
  <td>
    <input class="textfield text long-field" id="customfield_11202_` + rows + `_ct2" name="customfield_11202_` + rows + `_ct2" maxlength="254" type="text" value="" placeholder="Пусто">
  </td>
  <td>
    <input class="textfield text long-field" id="customfield_11202_` + rows + `_ct3" name="customfield_11202_` + rows + `_ct3" maxlength="254" type="text" value="" placeholder="Пусто">
  </td>
  <td>
    <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 16 16" id="customButtonRemoveRows_` + rows + `">
      <g fill="none" fill-rule="evenodd">
        <path d="M0 1.994C0 .893.895 0 1.994 0h12.012C15.107 0 16 .895 16 1.994v12.012A1.995 1.995 0 0 1 14.006 16H1.994A1.995 1.995 0 0 1 0 14.006V1.994z" fill="#36B37E" />
        <path d="M3.5 9a1 1 0 1 1 0-2h9a1 1 0 0 1 0 2h-9z" fill="#FFF" fill-rule="nonzero" />
      </g><a xmlns="http://www.w3.org/2000/svg" id="anchor" xlink:href="#" xmlns:xlink="http://www.w3.org/1999/xlink" target="_top">
        <rect x="0" y="0" width="100%" height="100%" fill-opacity="0"></rect>
      </a>
    </svg>
  </td>
</tr>`);
  $("[id^=customButtonRemoveRows]").click(function() {
    $(`#` + this.id).parent("td").parent("tr").remove();
    updateField();
  });
  $("input[id^=customfield_11202_]").keyup(function(){
	updateField();
  });
  $("input[id^=customfield_11202_]").change(function(){
    updateField();
  });
});
