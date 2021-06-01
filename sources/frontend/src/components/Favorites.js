import React, { useState } from 'react'
import { FaAngleDoubleRight } from 'react-icons/fa';
import { BsChevronDoubleDown } from 'react-icons/bs';
import jobs from '../data/jobs.json';

function Favorites() {
  const [value, setValue] = useState(0);

  const nextJob = () => {
      const next = value + 1;
      if (next <= jobs.length -1) {
          setValue(next);
      } else {
          setValue(0);
      }
  }

  const { company, dates, duties, title, companyDescription } = jobs[value]
  return (
      <section className='section'>
        <div className='title'>
          <h2>recipes</h2>
          <div className='underline'></div>
        </div>
        <div className='jobs-center'>
          {/* btn container */}
          <div className='btn-container'>
            {jobs.map((item, index) => {
              return (
                <button
                  key={item.id}
                  onClick={() => setValue(index)}
                  className={`job-btn ${index === value && 'active-btn'}`}
                >
                  {item.company}
                </button>
              )
            })}
          </div>
          {/* job info */}
          <article className='job-info'>
            <h3>{title}</h3>
            <h4>{company}</h4>
            <h5>{companyDescription}</h5>
            <p className='job-date'>{dates}</p>
            {duties.map((duty, index) => {
              return (
                <div key={index} className='job-desc'>
                  <FaAngleDoubleRight className='job-icon'></FaAngleDoubleRight>
                  <p>{duty}</p>
                </div>
              )
            })}
          </article>
        </div>
        <button type='button' className='btn' onClick={nextJob}>
          <BsChevronDoubleDown />
        </button>
      </section>
  )
}

export default Favorites
