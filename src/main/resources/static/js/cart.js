$(document).ready(function () {
$('.quantity').change(function () {
let quantity = $(this).val();
let id = $(this).attr('data-id');
$.ajax({url: '/cart/updateCart/' + id + '/' + quantity,
        type: 'GET',
        success: function () {
        location.reload();
   }
  });
 });
});