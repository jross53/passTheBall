$(function() {
    var showcase = $("#showcase");

    showcase.Cloud9Carousel( {
        yOrigin: 42,
        yRadius: 40,
        itemClass: "card",
        buttonLeft: $(".nav.left"),
        buttonRight: $(".nav.right"),
        autoPlay: 0,
        bringToFront: true,
        onLoaded: function() {
            showcase.css( 'visibility', 'visible' );
            showcase.css( 'display', 'none' );
            showcase.fadeIn( 1500 );
        }
    } );

    //
    // Simulate physical button click effect
    //
    $('.nav').click( function( e ) {
        var b = $(e.target).addClass( 'down' );
        setTimeout( function() { b.removeClass( 'down' ) }, 80 );
    } );
	
	$("*").doubleTap(function(){
			alert("You double tapped");
	});

    //$('.nav.left').on("click touchstart", function() {
    //
    //});

    var lastSwipeTime;
	
	var timeDifferenceMinimum = 25;

    $(document).on('pageinit', function(event){
        $("*").on("swipeleft", function() {
            var currentSwipe = new Date();
            if((currentSwipe - lastSwipeTime) >= timeDifferenceMinimum) {
                //$('.nav.right').click();
                $('.nav.right').trigger("click");
            }
            lastSwipeTime = currentSwipe;

        });

        $("*").on("swiperight", function() {
            var currentSwipe = new Date();
            if((currentSwipe - lastSwipeTime) >= timeDifferenceMinimum) {
                //$('.nav.left').click();
                $('.nav.left').trigger("click");
            }
            lastSwipeTime = currentSwipe;

        });


        /*var lastMouseDownX;
        $("*").on("vmousedown", function(e) {
            lastMouseDownX = e.pageX;
        });

        $(document).on("vmouseup", function(e) {
            if(lastMouseDownX > e.pageX) {
                $('.nav.left').click();
            } else if(lastMouseDownX < e.pageX) {
                $('.nav.right').click();
            } else {
                console.log("mouse did not move");
            }
        });*/
    });



    $(document).keydown( function( e ) {
        //
        // More codes: http://www.javascripter.net/faq/keycodes.htm
        //
        switch( e.keyCode ) {
            /* left arrow */
            case 37:
                $('.nav.left').click();
                break;

            /* right arrow */
            case 39:
                $('.nav.right').click();
        }
    } );
});