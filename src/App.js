import React, { useEffect, useState } from 'react';

function App() {
    const [data, setData] = useState(null);

    useEffect(() => {
        // JSP에서 서버사이드 렌더링을 통해 전달된 데이터가 있다면 사용
        if (typeof window.boundaryData !== 'undefined') {
            setData(window.boundaryData);
        } else {
            // 서버에서 데이터가 없을 경우 fallback으로 API 호출
            fetch('/api/boundary')
                .then(response => response.json())
                .then(result => setData(result))
                .catch(error => console.error('Error fetching data:', error));
        }
    }, []);

    return (
        <div>
            <h1>React와 Spring 연동 맵 페이지</h1>
            {data ? (
                <pre>{JSON.stringify(data, null, 2)}</pre>  // 서버에서 받아온 경계 데이터 출력
            ) : (
                <p>데이터 로딩 중...</p>
            )}
        </div>
    );
}

export default App;


// 수정 전 코드
// import React, { useEffect, useState } from 'react';
//
// function App() {
//     const [data, setData] = useState(null);
//
//     useEffect(() => {
//         // JSP에서 서버사이드 렌더링을 통해 전달된 데이터가 있다면 사용
//         if (typeof window.boundaryData !== 'undefined') {
//             setData(window.boundaryData);
//         } else {
//             // 서버에서 데이터가 없을 경우 fallback으로 API 호출
//             fetch('/api/boundary')
//                 .then(response => response.json())
//                 .then(result => setData(result))
//                 .catch(error => console.error('Error fetching data:', error));
//         }
//     }, []);
//
//     return (
//         <div>
//             <h1>React와 Spring 연동 맵 페이지</h1>
//             {data ? (
//                 <pre>{JSON.stringify(data, null, 2)}</pre>  // 서버에서 받아온 경계 데이터 출력
//             ) : (
//                 <p>데이터 로딩 중...</p>
//             )}
//         </div>
//     );
// }
//
// export default App;
