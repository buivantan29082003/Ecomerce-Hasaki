import logo from './logo.svg';
import './App.css';
import '../src/index'
import AppMain from './routes/App';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
function App() {
  return (
    <BrowserRouter>
      <AppMain/>
      <ToastContainer
        position="top-center"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop
        closeOnClick
        style={{zIndex:9292929292992929}}
        pauseOnHover
        theme="colored"
      />
    </BrowserRouter>
  );
}

export default App;
