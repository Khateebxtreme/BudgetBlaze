import * as React from 'react';

const FeatureCard = ({ title, desc, imageUrl }) => {
  return (
<div className="max-w-xl rounded-xl overflow-hidden shadow-lg bg-white border border-gray-300 hover:shadow-3xl lg:transition lg:duration-300 lg:ease-in-out lg:transform lg:hover:scale-105 py-4">
  <img className="w-full max-h-[500px] xl:h-[400px] 2xl:h-[500px] object-contain" src={imageUrl} alt="Card Image"/>

  <div className="px-6 py-4">
    <h2 className="text-xl 2xl:text-2xl font-semibold text-gray-800 mb-2 flex items-center justify-center">{title}</h2>
    
    <p className="text-[#6E6E6E] text-sm 2xl:text-lg  flex items-center justify-center">
      {desc}
    </p>
  </div>
</div>
  );
};

export default FeatureCard;
