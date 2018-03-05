/**
 * description : 自动配置并启动文本<strong>敏感词</strong>校验 jQuery 插件
 * require : jQuery
 * example :
 *
 *      1. 页面配置隐藏域获取 access_token
 *          <input type="hidden" class="_access_token_" value="access_token">
 *
 *      2. 需要开启的控件配置 class 属性 'sensitiv-words-validate', 或者自定义初始化
 *      $('.sensitiv-words-validate').sensitivWords({
                auto : true // 页面打开时自动校验文本
            });
 * owner    : minsu
 * version : 1.0.0
 */
(function($) {

    'use strict';

    var SensitivWords = function (selector,options) {
        this.$element = $(selector);
        this.options = $.extend({}, $.fn.sensitivWords.defaults, options);
        this.$result = $(this.options.result);
        this.isback = false;// todo

        this.sensitive = false;
        this.words = [];
        this.message = '';

        this.init();
    }

    SensitivWords.prototype = {
        tokenExample : '<input type="hidden" class="_access_token_" value="">',
        init : function () {
            this.onload()
                .listen();
        },
        onload : function () {
            var that = this;
            if (that.options.auto && that.options.auto === true) {
                that.validateHandler();
            }
            return this;
        },
        autoValidate : function () {
            var that = this;
            if (that.options.auto) {

            }
            return this;
        },
        validateHandler : function () {
            var that = this,
                options = this.options,
                xhr,
                $token = options.accessTokenElement && (options.accessTokenElement = $(options.accessTokenElementSelector));
            /*if (options.isback && options.isback===true) {
                return
            }*/
            var Data = $.extend({content: that.$element.val().trim()}, options.dataParams, {});

            if(xhr && xhr.readyState != 4){
                xhr.abort();
            }

            xhr = $.ajax({
                async : true,
                method: options.method,
                url: options.url,
                data : JSON.stringify(Data),
                dataType: "json",
                crossDomain: true,
                processData : false,
                headers:{
                    "content-type": "application/json",
                    'authorization' : $token.val()
                }
            })

            xhr.done(function (d,s,x) {
                if (d && d.code === options.successCode) {
                    options.isback = true;
                    that.resultResolver({
                        words : d.data.ikList,
                        sensitive : d.data.sensitive,
                        message : d.data.message
                    });

                    that.resultHandle(d);
                }
            }).fail(function (x,s,a) {
                options.isback = true;
                that.resultResolver({message : x.responseJSON.message||x.statusText});
                that.resultHandle();
            })
        },
        resultResolver : function (param) {
            try {
                this.sensitive = param.sensitive || false;
                this.words = param.words || [];
                this.message = param.message || [];
            } catch (e) {
                console.error(e);
            }
            return this;
        },

        resultHandle :function (d) {
            var that = this,
                pos = $.extend({}, that.$element.position(), {
                height: that.$element[0].offsetHeight
            });
            that.$result.insertAfter(that.$element).css({
                top: pos.top + pos.height,
                left: pos.left
            }).css(that.options.styles).show();

            if (that.sensitive && that.sensitive === true) {
                that.render();
            } else {
                that.render(null, that.message);
            }
            // 标注凸显敏感词
            /*that.$element.val(
                that.contentTemplate(d.content,that.$element.val())
            )*/
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
        render: function (items, title) {
            var that = this,_title, items = items || this.words;
            if (items && items.length>0) {
                items = $(items).map(function (i, item) {
                    i = $(that.options.item).attr({
                        count : item.count,
                        word : item.word
                    })
                    i.find('code').html(that.highlighter(item.word, item.word))
                    return i[0]
                });
            }
            _title = title || this.options.successTitle;
            if (items && items.length>0) {
                _title = this.options.warnTitle;
            }
            this.$result.find('label').text(_title);
            this.$result.find('p').html(items);
            return this
        },
        highlighter: function (item,query) {
            var that = this;
            query = query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
                return '<strong style="color:#369;">' + match + '</strong>';
            })
        },
        listen: function () {
            // 暂时搞一个blur事件
            this.$element
                .on('blur', $.proxy(this.blur, this))
                .on('keypress', $.proxy(this.keypress, this))
                .on('keyup', $.proxy(this.keyup, this))
            if (this.eventSupported('keydown')) {
                this.$element.on('keydown', $.proxy(this.keydown, this))
            }
        },
        eventSupported: function (eventName) {
            var isSupported = eventName in this.$element
            if (!isSupported) {
                this.$element.setAttribute(eventName, 'return;')
                isSupported = typeof this.$element[eventName] === 'function';
            }
            return isSupported;
        },
        blur : function () {
            this.validateHandler();
        }
    };

    $.fn.sensitivWords = function (option) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('sensitivWords'),
                options = typeof option == 'object' && option
            if (!data) {
                $this.data('sensitivWords', (data = new SensitivWords(this, options)))
            }
            if (typeof option == 'string') {
                data[option]()
            }
        })
    }

    $.fn.sensitivWords.defaults = {
        method : 'POST',
        url : 'http://eunomia.ziroom.com/api/sensitive',
        // url : 'http://127.0.0.1:9090/api/sensitive',
        successCode : 10000,
        auto : false,
        accessTokenElementSelector : '._access_token_',// 2选一
        accessTokenElement : {},
        dataParams :{},
        successTitle : '暂未发现违规词项',
        warnTitle : '检测到敏感词',
        authorizedMessage : '授权码失效!',
        styles : {
            'border-left-color': '#ce4844',
            'padding': '20px',
            'margin': '20px 0',
            'border': '1px solid #eee',
            'border-left-width': '5px',
            'border-radius': '3px'
        },
        result : "<div class=''><label></label><p></p></div>",
        item : "<span><code></code></span>",
        delay: 1000
    }

    /***
     * sensitiv word init example
     */
    $('.sensitiv-words-validate').sensitivWords({
        auto : true
    });

}(jQuery))