import { useState, useEffect, useCallback } from "react";
import axios from "axios";

axios.defaults.baseURL = process.env.REACT_APP_API_URL + "api/";

const useApiRequest = (initData, initConfig) => {
  // loading, error ...
  const [initialData] = useState(initData);
  const [data, setData] = useState(initData);
  const [requestConfig, setRequestConfig] = useState(initConfig);

  const retry = useCallback((requestConfig) => {
    setRequestConfig(requestConfig);
  }, []);

  useEffect(() => {
    const callApi = async () => {
      try {
        const result = await axios(requestConfig);
        setData(result.data);
      } catch (error) {
        setData(initialData);
      }
    };

    requestConfig && callApi();
  }, [requestConfig, initialData]);

  return [retry, data];
};

export default useApiRequest;
