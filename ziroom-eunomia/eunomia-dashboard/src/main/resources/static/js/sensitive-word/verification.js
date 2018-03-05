'use strict';

+function () {

    var sinsitiv = {

        $form : $("#form"),

        validate : {},

        init : function () {
            this.config();

            return this
                .onload()
                .btnCheckEvent();
        },

        config : function () {
            this.validate = this.validateResolvers();

            return this;
        },

        onload : function () {

            var wordDiv = $('.div-sensitiv-message');
            wordDiv.hide();

            this.testSetting();
            return this;
        },

        testSetting : function () {

            $('.textarea-check').val(
                '陈水扁保外就医，作为台独文化的始作俑者，终将自食其果，我国外交部再次申明，台湾是中国的固有领土，不排除动用一切武力维护领土主权！'
            );
            return this;
        },

        btnCheckEvent : function () {
            var s = this;
            $('.btn-check').on('click', function (e) {
                var content = $('.textarea-check').val();
                if (s.validate.valid() === true) {
                    s.checkHandle(content);
                }
            });
        },

        checkHandle : function (content) {
            var s = this;

            $.post('/sensitive',{'content' : content},function (data) {
                try {
                    if (data && data.code === 10000) {
                        data.origin = content
                        s.checkCallBack(data);
                    } else if (data && data.code === 1) {
                        alert("没有权限！" + data.msg);
                    } else {
                        alert(data.message || data.msg);
                    }
                } catch (e) {
                    console.error(e)
                }
            });

            return this;
        },

        checkCallBack : function (data) {
            var $div = $('.div-sensitiv-message'),
                $modalContent = $('.modal-content'),
                $modalWords = $('.modal-words'),
                s = this,
                origin = data.origin,
                _data = data.data;

            if (_data.sensitive && _data.sensitive === true) {
                $div.show(500)
                    .find('.m-ms')
                    .text(_data.ikList.length);
                $div.show(500)
                    .find('small')
                    .text('我们检测到可能有需要改动词，请参考调整！');


                $modalWords.find('p')
                    .empty();
                $modalWords.find('p')
                    .append(
                        s.wordsTemplate(_data.ikList).template
                    );

                $modalContent.find('p')
                    .empty();
                $modalContent.find('p')
                    .append(
                        s.contentTemplate(_data, origin)
                    )
            } else {
                $div.show(500)
                    .find('small')
                    .text('没有发现敏感词！');
                $div.find('.m-ms')
                    .text(0);
                $modalWords.find('p')
                    .empty();
                $modalContent.find('p')
                    .empty();
                $modalContent.find('p')
                    .append(
                        origin
                    )
            }


        },

        contentTemplate : function (list, origin) {
            var s = this, words = [], template = origin;
            if (!list || typeof list !== 'object' ) {
                return;
            }
            return function () {
                $.each(list.ikList, function (i,item) {
                    template = s.highlighter(template,item.word);
                })
                return template;
            }();
        },

        wordsTemplate : function (list) {
            var words = [], matchCout = 0;
            if (!list || typeof list !== 'object' ) {
                return;
            }
            return function () {
                $.each(list, function (i,item) {
                    matchCout+=item.count;
                    words.push('<code>'+item.word+'</code>')
                })
                return {
                    matchs : matchCout,
                    template : words.join('')
                }
            }();
        },

        highlighter: function (item,query) {
            var that = this;
            query = query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
                return '<code>' + match + '</code>'
            })
        },

        validateResolvers :function () {

            return this.$form.validate({
                rules: {
                    min: {
                        required: true,
                        minlength: 2
                    }
                }
            });
        }

    }

    sinsitiv.init();

}(jQuery, window);