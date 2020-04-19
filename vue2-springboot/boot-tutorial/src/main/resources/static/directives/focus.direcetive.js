// v-focus 라는 전역 사용자 정의 지시자 등록하기
Vue.directives('focus', {
    // 바인딩된 요소가 DOM으로 삽입될 때
    inserted: function(el) {
        // 요소에 초점을 둔다
        el.focus()
    }
})