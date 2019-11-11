var mainField = "#customfield_11413";
var array = ['#customfield_11401', '#customfield_11217', '#customfield_11405', '#customfield_11406', '#customfield_11407', '#customfield_11410','#customfield_11722'];
var eqDict = {
  'Телефонный аппарат': ['#customfield_11401', '#customfield_11217'],
  'Модем': ['#customfield_11405'],
  'SIM': ['#customfield_11406', '#customfield_11407'],
  'Bluetooth': ['#customfield_11410'],
  'Прочее': ['#customfield_11722'],
};
var dropArray = ['#customfield_11401', '#customfield_11217', '#customfield_11407'];

$("#customfield_11401").parent().append('<br><a href="http" target=black>Link</a>');
$("#customfield_11401").addClass('long-field');

$.each(array, function(i, l) {
  $(l).parent('div').hide();
  $(l).parent('div').children("label").append('<span class="aui-icon icon-required">Required</span>');
});

$.each($('input[name='+mainField.replace('#','')+'], label[for^='+mainField.replace('#','')+']'), function() {
  $(this).click(function() {
    if ($(this).is(':checked')) {
      checkbox = this;
      $.each(eqDict, function(i, l) {
        if ($(checkbox).next('label').text() == i) {
          $.each(l, function(j, k) {
            $(k).parent('div').show();
          })
        }
      })
    } else {
      checkbox = this;
      $.each(eqDict, function(i, l) {
        if ($(checkbox).next('label').text() == i) {
          $.each(l, function(j, k) {
            $(k).parent('div').hide();
            if (k in dropArray) {
              $(k).get(0).selectedIndex = 0;
            } else {
              $(k).val('');
            }
          })
        }
      })
    }
  })
});

$.each($('input[name='+mainField.replace('#','')+'], label[for^='+mainField.replace('#','')+']'), function() {
    if ($(this).is(':checked')) {
      checkbox = this;
      $.each(eqDict, function(i, l) {
        if ($(checkbox).next('label').text() == i) {
          $.each(l, function(j, k) {
            $(k).parent('div').show();
          })
        }
      })
    } else {
      checkbox = this;
      $.each(eqDict, function(i, l) {
        if ($(checkbox).next('label').text() == i) {
          $.each(l, function(j, k) {
            $(k).parent('div').hide();
            if (k in dropArray) {
              $(k).get(0).selectedIndex = 0;
            } else {
              $(k).val('');
            }
          })
        }
      })
    }
});
