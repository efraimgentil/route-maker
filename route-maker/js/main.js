$(function(){

  $(window).resize(function(){
    adaptPageWraper();
  });

  function adaptPageWraper(){
    $('#page-wrapper').css({ "height": (window.innerHeight - $("#topNavBar").height() ) } );
  };

  adaptPageWraper();

});