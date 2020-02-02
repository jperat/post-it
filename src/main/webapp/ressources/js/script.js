$(function() {
	$(document).on("submit", "form[name='new_post_form']", function(e) {
		e.preventDefault();
		var formData = new FormData($(this)[0]);
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		$.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
			url : $(this).attr("action"),
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			beforeSend: function(xhr){
		        xhr.setRequestHeader(header, token);
		    },
			success : function(response) {
				console.log(response);
				if (response.code == "200") {
					$("#new_post").html(response.render);
				} else {
					$("#new_post").html(response.render);
				}
			}
		});
	});

	$(document).on("submit", "form[name='follow_form']", function(e) {
		e.preventDefault();
		var id = $(this).attr("profile_id");
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function(response) {
				if (response.code == "200") {
					var follow = parseInt($('#follow_' + id).text());
					follow = follow + 1;
					$('#follow_' + id).text(follow + "");
					$("#follow_form_" + id).hide();
					$("#unfollow_form_" + id).show();
				}
			}
		})
	});

	$(document).on("submit", "form[name='unfollow_form']", function(e) {
		e.preventDefault();
		var id = $(this).attr("profile_id");
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function(response) {
				if (response.code == "200") {
					var follow = parseInt($('#follow_' + id).text());
					follow = follow - 1;
					$('#follow_' + id).text(follow + "");
					$("#follow_form_" + id).show();
					$("#unfollow_form_" + id).hide();
				}
			}
		})
	});

	$(document).on("submit", "form[name='like_form']", function(e) {
		e.preventDefault();
		var id = $(this).attr("post_id");
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function(response) {
				if (response.code == "200") {
					$("#like_form_" + id).hide();
					$("#unlike_form_" + id).show();
					var like = parseInt($('#post_like_' + id).text());
					like = like + 1;
					$('#post_like_' + id).text(like + "");
				}
			}
		})
	});

	$(document).on("submit", "form[name='unlike_form']", function(e) {
		e.preventDefault();
		var id = $(this).attr("post_id");
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function(response) {
				if (response.code == "200") {
					$("#like_form_" + id).show();
					$("#unlike_form_" + id).hide();
					var like = parseInt($('#post_like_' + id).text());
					like = like - 1;
					$('#post_like_' + id).text(like + "");
				}
			}
		})
	});

	$(document).on("submit", "form[name='new_comment_form']", function(e) {
		e.preventDefault();
		var id = $(this).attr("post_id");
		var $this = $(this);
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function(response) {
				if (response.code == "400") {
					$("#comment_form_" + id).replaceWith(response.render);
				} else if (response.code == "200") {
					$this[0].reset();
					$("#comment_list_" + id).prepend(response.render);
				}
			}
		});
	});
	
	$(document).on("submit", ".account-form", function (e) {
		e.preventDefault();
		$this = $(this);
		$.ajax({
			url : $(this).attr("action"),
			type : $(this).attr("method"),
			data : $(this).serialize(),
			success : function (response) {
				$this.replaceWith(response.render);
			}
		});
	});
	
	$(document).on("submit", "form[name='avatar_form']", function(e) {
		e.preventDefault();
		$this = $(this);
		var formData = new FormData($(this)[0]);
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		$.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
			url : $(this).attr("action"),
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			beforeSend: function(xhr){
		        xhr.setRequestHeader(header, token);
		    },
			success : function(response) {
				$this.replaceWith(response.render);
			}
		});
	});

	$(document).on("click", ".remove-post", function(e) {
		e.preventDefault();
		var id = $(this).data('id');
		$.ajax({
			url : '/post-it/post/remove/' + id,
			type : 'delete',
			beforeSend: function(xhr){
		        xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
		    },
			success : function(response) {
				if (response.code == "200") {
					$("#post_" + id).remove();
				}
			}
		})
	})

	$(document).on("click", ".remove-comment", function(e) {
		e.preventDefault();
		var id = $(this).data('id');
		$.ajax({
			url : '/post-it/comment/remove/' + id,
			type : 'delete',
			beforeSend: function(xhr){
		        xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
		    },
			success : function(response) {
				if (response.code == "200") {
					$("#comment_" + id).remove();
				}
			}
		})
	})
})