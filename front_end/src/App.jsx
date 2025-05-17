import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Header from './components/Header/header.jsx';  // 헤더 경로 확인
import Router from './routes/Router';             // 라우팅 컴포넌트

const App = () => {
  return (
    <BrowserRouter>
      <Header />     {/* 항상 상단에 표시될 헤더 */}
      <Router />     {/* 페이지에 따라 바뀌는 내용 */}
    </BrowserRouter>
  );
};

export default App;
