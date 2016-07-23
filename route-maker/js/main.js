function adaptPageWraper(){
  $('#page-wrapper').css({ "height": (window.innerHeight - $("#topNavBar").height() ) } );
};

$(function(){
  $(window).resize(function(){
//    adaptPageWraper();
  });
//  adaptPageWraper();
});